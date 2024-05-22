package com.example.weather.schedule;


import com.example.weather.service.weather.WeatherService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  private final WeatherService weatherService;

  @Scheduled(cron = "0 8 * * * *")
  public void updateTemperatures() {
    log.info("updating the temperatures");
    weatherService.refreshWeather(Optional.empty(), Optional.empty());

  }
}