package org.test.api.controller;

import org.springframework.web.bind.annotation.*;
import org.test.api.response.Response;
import org.test.api.response.StatusEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.test.member.entity.Member;
import org.test.member.service.MemberService;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/webFlux")
public class WebFluxAPI {

    private final MemberService memberService;

    public WebFluxAPI(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/helloWebFlux")
    public Mono<Map<String, String>> helloWebFlux() {
        return Mono.just(Map.of("status", "OK"));
    }

    @GetMapping("/webFluxData")
    public Mono<Response> webFluxData(final String id) {
        Optional<Member> result = Optional.of(memberService.getUserById(id).orElse(new Member()));

        Response response = Response.builder()
                .status(StatusEnum.OK)
                .message("success")
                .data(result)
                .build();

        return Mono.just(response);
    }
}
