package com.example.weather.exception;

import java.time.Instant;
import lombok.Data;

@Data
public class ApiError {

  int httpCode;
  String message;
  Instant timestamp;
}
