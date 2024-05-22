package com.example.weather.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.weather.data.dto.WeatherDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.service.weather.WeatherService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class WeatherControllerTest {

  @InjectMocks
  private WeatherController weatherController;
  @Mock
  private WeatherService weatherService;

  private ResponseEntity<Page<WeatherDto>> weatherPage;

  @BeforeEach
  void setUp() {
    Pageable pageable = PageRequest.of(0, 20, Direction.ASC, "id");
    Page<WeatherDto> page = new PageImpl<>(List.of(WeatherDto.builder().build()), pageable, 100);
    weatherPage = ResponseEntity.ok().body(page);

  }

  @Test
  void getWeatherForAllCities() {

    Mockito.when(weatherService.getWeatherPageForAllCities(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(weatherPage);

    assertEquals(
        weatherPage, weatherController.getWeatherPageForAllCities(Optional.empty(), Optional.empty()));

  }

  @Test
  void deleteAllCities() {

    Mockito.when(weatherService.removeAll()).thenReturn(ResponseEntity.ok("Success"));

    assertEquals(ResponseEntity.ok("Success"), weatherController.deleteAllCities());
  }

  @Test
  void refreshWeatherAllCities() {

    Mockito.when(weatherService.refreshWeather(ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(ResponseEntity.ok("Success"));

    assertEquals(ResponseEntity.ok("Success"), weatherController.refreshWeatherAllCities(Optional.empty(), Optional.empty()));

  }
}