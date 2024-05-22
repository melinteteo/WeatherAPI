package com.example.weather.exception;

public class WeatherBadRequestException extends RuntimeException{

  public WeatherBadRequestException(String msg) {
    super(msg);
  }
}
