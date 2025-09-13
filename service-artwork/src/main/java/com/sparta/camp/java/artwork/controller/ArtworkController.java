package com.sparta.camp.java.artwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/artworks")
public class ArtworkController {

  @GetMapping("/health")
  public ResponseEntity<Map<String, Object>> health() {
    return ResponseEntity.ok(Map.of(
        "service", "artwork-service",
        "status", "UP",
        "timestamp", System.currentTimeMillis()));
  }
}
