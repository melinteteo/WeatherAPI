package com.example.weather.exception;

public class WeatherServiceException extends RuntimeException {

  public WeatherServiceException(String msg) {
    super(msg);
  }

  public WeatherServiceException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
