package com.example.weather.config;

import com.example.weather.exception.WeatherInitException;
import com.example.weather.service.weather.WeatherService;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

  private final WeatherService weatherService;

  @PostConstruct
  public void init() {
    log.info("creating the initial records");
    try {
      weatherService.initWeather();
    } catch (IOException e) {
      throw new WeatherInitException(e.getLocalizedMessage(), e);
    }
  }
}