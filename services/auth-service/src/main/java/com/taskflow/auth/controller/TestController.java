package com.taskflow.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TestController {

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-Username") String username) {

        Map<String, Object> response = new HashMap<>();

        response.put("message", "JWT Validation Successful");
        response.put("userId", userId);
        response.put("username", username);

        return ResponseEntity.ok(response);
    }

}