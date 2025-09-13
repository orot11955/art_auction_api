package com.sparta.camp.java.common.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponse {
  private String errorCode;
  private String message;
  private Map<String, Object> details;
  private long timestamp;
}

