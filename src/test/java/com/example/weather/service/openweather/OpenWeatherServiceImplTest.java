package com.example.weather.service.openweather;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.weather.data.dto.openweather.MainDto;
import com.example.weather.data.dto.openweather.OpenWeatherResponseDto;
import com.example.weather.data.entity.WeatherEntity;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
class OpenWeatherServiceImplTest {

  private OpenWeatherServiceImpl openWeatherService;

  private Flux<OpenWeatherResponseDto> openWeatherResponseDtoFlux;
  private List<WeatherEntity> weatherEntityList;

  @BeforeEach
  void setUp() {
    openWeatherService = Mockito.spy(new OpenWeatherServiceImpl(WebClient.builder().build()));
    openWeatherResponseDtoFlux = Flux.fromIterable(
        List.of(OpenWeatherResponseDto.builder().id(1234L).name("Bucharest").main(
            MainDto.builder().humidity(80).temp(12).temp_min(8).temp_max(15).build()).build()));
    weatherEntityList = List.of(
        WeatherEntity.builder().cityId(1234L).cityName("Bucharest").humidity(80).maxTemperature(15).temperature(12)
            .minTemperature(8).build());
  }

  @Test
  void getWeatherForFirst100Cities() {
    Mockito.doReturn(openWeatherResponseDtoFlux).when(openWeatherService)
        .getWeatherForAllCities(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    assertEquals(weatherEntityList,
        openWeatherService.getWeatherForFirst100Cities(weatherEntityList, Optional.empty(), Optional.empty()));
  }

}