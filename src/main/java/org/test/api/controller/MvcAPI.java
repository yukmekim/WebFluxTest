package org.test.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.test.api.response.Response;
import org.test.api.response.StatusEnum;
import org.test.member.entity.Member;
import org.test.member.service.MemberService;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v2/mvc")
public class MvcAPI {

    private final MemberService memberService;

    public MvcAPI(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/helloMvc")
    public Map<String, String> helloMvc() {
        return Map.of("status", "200");
    }

    @GetMapping("/mvcData")
    public ResponseEntity<Response> testFlux(final String id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8));

        Optional<Member> result = Optional.of(memberService.getUserById(id).orElse(new Member()));

        Response response = Response.builder()
                .status(StatusEnum.OK)
                .message("success")
                .data(result)
                .build();

        return new ResponseEntity<>(response, header, HttpStatus.OK);
    }
}
