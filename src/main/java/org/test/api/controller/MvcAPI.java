package org.test.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v2/mvc")
public class MvcAPI {
    @GetMapping("/helloMvc")
    public Map<String, String> helloMvc() {
        return Map.of("status", "200");
    }
}
