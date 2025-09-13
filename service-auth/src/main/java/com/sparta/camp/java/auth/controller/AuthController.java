package com.sparta.camp.java.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    return ResponseEntity.ok(Map.of(
        "service", "auth-service",
        "status", "UP",
        "timestamp", System.currentTimeMillis()));
  }
}
