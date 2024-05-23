package com.example.weather.config;

import com.example.weather.utils.WeatherConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class Config {

  @Bean
  WebClient createOpenWeatherWebClient() {
    log.info("creating OpenWeather web client");
    return WebClient.create(WeatherConstants.OPEN_WEATHER_HOST);
  }
}
