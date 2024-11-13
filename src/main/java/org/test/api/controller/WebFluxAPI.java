package org.test.api.controller;

import org.springframework.web.bind.annotation.*;
import org.test.api.response.Response;
import org.test.api.response.StatusEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/webFluxApi")
public class WebFluxAPI {

    @GetMapping("/testFlux")
    public ResponseEntity<Response> testFlux() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));

        Response response = Response.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();

        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }

    @GetMapping("/helloWebFlux")
    public Mono<Map<String, String>> helloWebFlux() {
        return Mono.just(Map.of("status", "200"));
    }
}
