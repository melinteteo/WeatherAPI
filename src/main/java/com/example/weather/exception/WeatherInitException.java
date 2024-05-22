package com.example.weather.exception;

public class WeatherInitException extends RuntimeException {

  public WeatherInitException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
