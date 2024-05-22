package com.example.weather.service.weather;

import com.example.weather.data.dto.CityDto;
import com.example.weather.data.dto.WeatherDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.data.mapper.WeatherMapper;
import com.example.weather.exception.WeatherBadRequestException;
import com.example.weather.exception.WeatherServiceException;
import com.example.weather.repository.WeatherRepository;
import com.example.weather.service.openweather.OpenWeatherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

  private final OpenWeatherService openWeatherService;
  private final WeatherRepository weatherRepository;
  private final ObjectMapper objectMapper;
  private final WeatherMapper weatherMapper;


  @Override
  public void initWeather() throws IOException {

    List<CityDto> citiesList = objectMapper.readValue(this.getClass().getResource("/static/city.list.json"),
        new TypeReference<>() {
        });

    List<CityDto> first100GCitiesList = citiesList.subList(0, 100);

    List<WeatherEntity> listOfFirst100WeatherByCityEntity = first100GCitiesList.stream()
        .map(cityDto -> WeatherEntity.builder().cityId(cityDto.getId()).cityName(cityDto.getName()).build()).toList();

    Iterable<WeatherEntity> openWeatherResponses = openWeatherService.getWeatherForFirst100Cities(
        listOfFirst100WeatherByCityEntity, Optional.empty(), Optional.empty());

    weatherRepository.saveAll(openWeatherResponses);
  }

  @Override
  public ResponseEntity<Page<WeatherDto>> getWeatherPageForAllCities(Optional<Integer> page, Optional<String> sortBy) {
    Set<String> criteriaToSortBy = Set.of("cityName", "temperature", "humidity", "maxTemperature", "minTemperature",
        "id");
    if (sortBy.isPresent() && !criteriaToSortBy.contains(sortBy.get())) {
      throw new WeatherBadRequestException(
          "sorting criteria not existing, please try one from this list: " + criteriaToSortBy);
    }
    Page<WeatherEntity> weatherEntityPage;
    Page<WeatherDto> weatherDtoPage;
    try {
      Pageable pageable = PageRequest.of(page.orElse(0), 20, Direction.ASC, sortBy.orElse("id"));
      weatherEntityPage = weatherRepository.findAll(pageable);
      weatherDtoPage = new PageImpl<>(weatherMapper.toDtoList(weatherEntityPage.getContent()), pageable,
          weatherEntityPage.getTotalElements());
    } catch (Exception e) {
      throw new WeatherServiceException(e.getLocalizedMessage(), e.getCause());
    }
    return ResponseEntity.ok(weatherDtoPage);
  }

  @Override
  public ResponseEntity<String> refreshWeather(Optional<String> units, Optional<String> languageCode) {
    List<WeatherEntity> weatherEntities = openWeatherService.getWeatherForFirst100Cities(
        weatherRepository.findAll(), units, languageCode);

    weatherRepository.saveAll(weatherEntities);

    return ResponseEntity.ok("Success");
  }

  @Override
  public ResponseEntity<String> removeAll() {
    try {
      weatherRepository.deleteAll();
    } catch (Exception e) {
      log.error("deletion failed: {}", e.getLocalizedMessage());
      throw new WeatherServiceException(e.getLocalizedMessage(), e);
    }
    return ResponseEntity.ok("Success");
  }
}
