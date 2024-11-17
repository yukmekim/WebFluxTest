package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.test.TestCodeApplication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestCodeApplication.class)
@AutoConfigureMockMvc
@AutoConfigureWebTestClient
class WebFluxTest {

    @Autowired
    private MockMvc mockMvc;

    private static final int NUMBER_OF_REQUESTS = 100; // 동시 요청수
    private static final String THREE_SECONDS_URL = "http://localhost:8099/api/v2/webFluxApi/testFlux";
    private static final String TEST_MVC_URL = "http://localhost:8099/api/v2/mvc/helloMvc";

    @Test
    @DisplayName("RestTemplate 응답 시간 테스트")
    public void blocking() {
        final RestTemplate restTemplate = new RestTemplate();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < NUMBER_OF_REQUESTS; i++) {
            final ResponseEntity<String> response =
                    restTemplate.exchange(THREE_SECONDS_URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            System.out.println(response.getBody());
        }

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }

    @Test
    @DisplayName("MVC 응답 시간 테스트")
    public void testConcurrentMvcRequests() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_REQUESTS); // 요청 수에 맞춰 카운트 설정
        final StopWatch stopWatch = new StopWatch();

        stopWatch.start(); // 측정 시작

        ExecutorService executorService = Executors.newFixedThreadPool(10); // 10개의 스레드 풀

        for (int i = 0; i < NUMBER_OF_REQUESTS; i++) {
            executorService.submit(() -> {
                try {
                    mockMvc.perform(get(TEST_MVC_URL))
                            .andExpect(status().isOk());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown(); // 요청 완료 시 카운트 다운
                }
            });
        }

        executorService.shutdown();
        countDownLatch.await(); // 모든 요청이 완료될 때까지 대기
        stopWatch.stop(); // 측정 종료

        System.out.println("Total time: " + stopWatch.getTotalTimeSeconds() + " seconds"); // 소요 시간 출력
    }

    @Test
    @DisplayName("WebFlux 응답 시간 테스트")
    public void nonBlocking() throws InterruptedException {
        final WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8099") // 기본 URI 설정
                .build();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_REQUESTS);

        for (int i = 0; i < NUMBER_OF_REQUESTS; i++) {
            webClient.get()
                    .uri("/api/v2/webFlux/helloWebFlux") // 상대 URI 사용
                    .retrieve()
                    .bodyToMono(String.class)
                    .doOnTerminate(countDownLatch::countDown) // 요청 완료 시 카운트 다운
                    .subscribe(it -> {
                        System.out.println(it);
                    });
        }

        countDownLatch.await(); // 모든 요청이 완료될 때까지 대기
        stopWatch.stop();  // 측정 종료
        System.out.println("Total time: " + stopWatch.getTotalTimeSeconds() + " seconds"); // 소요 시간 출력
    }
}