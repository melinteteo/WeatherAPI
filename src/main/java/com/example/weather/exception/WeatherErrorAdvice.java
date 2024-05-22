package com.example.weather.exception;

import java.time.Instant;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class WeatherErrorAdvice {

  @ExceptionHandler({WeatherServiceException.class})
  public ResponseEntity<Object> handleWeatherServiceException(WeatherServiceException e) {
    return buildResponseAndLogError(HttpStatus.INTERNAL_SERVER_ERROR, Arrays.toString(e.getStackTrace()));
  }

  @ExceptionHandler({WeatherBadRequestException.class})
  public ResponseEntity<Object> handleWeatherServiceException(WeatherBadRequestException e) {
    return buildResponseAndLogError(HttpStatus.BAD_REQUEST, e.getLocalizedMessage());
  }

  @ExceptionHandler({OpenWeatherApiCallException.class})
  public ResponseEntity<Object> handleOpenWeatherApiCallException(OpenWeatherApiCallException e) {
    return buildResponseAndLogError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
  }

  @ExceptionHandler({RuntimeException.class})
  public ResponseEntity<Object> handleRunTimeException(RuntimeException e) {
    return buildResponseAndLogError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
  }

  private ResponseEntity<Object> buildResponseAndLogError(HttpStatus status, String message) {
    log.error(message);
    if (message.contains("Failed to convert from type")) {
      message = "Make sure that you have input the correct data type";
      status = HttpStatus.BAD_REQUEST;
    }
    return buildResponseEntity(status, message);
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String errorMessage) {
    ApiError error = new ApiError();
    error.setHttpCode(status.value());
    error.setMessage(errorMessage);
    error.setTimestamp(Instant.now());
    return ResponseEntity.status(status).body(error);
  }
}
