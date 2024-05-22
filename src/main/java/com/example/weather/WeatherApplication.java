package com.example.weather;

import com.example.weather.utils.WeatherConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class WeatherApplication {

  public static void main(String[] args) {
    SpringApplication.run(WeatherApplication.class, args);
  }

  @Bean
  WebClient createOpenWeatherWebClient() {
    log.info("creating OpenWeather web client");
    return WebClient.create(WeatherConstants.OPEN_WEATHER_HOST);
  }

}
