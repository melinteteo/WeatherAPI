package com.example.weather.service.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.weather.data.dto.WeatherDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.data.mapper.WeatherMapper;
import com.example.weather.exception.WeatherServiceException;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.openweather.OpenWeatherService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class WeatherServiceImplTest {

  @InjectMocks
  private WeatherServiceImpl weatherService;
  @Mock
  private OpenWeatherService openWeatherService;
  @Mock
  private WeatherRepository weatherRepository;
  @Mock
  private WeatherMapper weatherMapper;

  private ResponseEntity<Page<WeatherDto>> weatherPage;
  private Page<WeatherEntity> weatherEntityPage;
  private List<WeatherDto> weatherDtoList;

  @BeforeEach
  void setUp() {
    Pageable pageable = PageRequest.of(0, 20, Direction.ASC, "id");
    weatherDtoList = List.of(WeatherDto.builder().build());
    Page<WeatherDto> weatherDtoPage = new PageImpl<>(weatherDtoList, pageable, 100);
    weatherPage = ResponseEntity.ok().body(weatherDtoPage);
    weatherEntityPage = new PageImpl<>(List.of(WeatherEntity.builder().build()), pageable, 100);
  }

  @Test
  void getWeatherPageForAllCities() {
    Mockito.when(weatherRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(weatherEntityPage);
    Mockito.when(weatherMapper.toDtoList(ArgumentMatchers.anyList())).thenReturn(weatherDtoList);
    assertEquals(weatherPage,
        weatherService.getWeatherPageForAllCities(Optional.empty(), Optional.empty()));
  }

  @Test
  void refreshWeather() {
    Mockito.when(
            openWeatherService
                .getWeatherForFirst100Cities(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
        .thenReturn(List.of(WeatherEntity.builder().build()));
    Mockito.when(weatherRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(weatherEntityPage);
    assertEquals(ResponseEntity.
        ok("Success"), weatherService.refreshWeather(Optional.empty(), Optional.empty()));
  }

  @Test
  void removeAll() {
    assertEquals(ResponseEntity.ok("Success"), weatherService.removeAll());

    Mockito.doThrow(RuntimeException.class).when(weatherRepository).deleteAll();
    assertThrows(WeatherServiceException.class, () -> weatherService.removeAll());
  }
}