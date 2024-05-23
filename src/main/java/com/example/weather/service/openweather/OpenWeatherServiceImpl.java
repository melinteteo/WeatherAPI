package com.example.weather.service.openweather;

import com.example.weather.data.dto.openweather.OpenWeatherResponseDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.exception.OpenWeatherApiCallException;
import com.example.weather.utils.WeatherConstants;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OpenWeatherServiceImpl implements OpenWeatherService {

  private final WebClient openWeatherClient;

  @Autowired
  public OpenWeatherServiceImpl(WebClient openWeatherClient) {
    this.openWeatherClient = openWeatherClient;
  }

  @Override
  public void updateWeatherEntityList(List<WeatherEntity> weatherEntityList,
      Optional<String> units, Optional<String> languageCode) {

    var openWeatherResponses = getWeatherForAllCitiesById(
        weatherEntityList.stream().map(WeatherEntity::getCityId).toList(), units,
        languageCode).toIterable();

    openWeatherResponses.forEach(openWeatherResponseDto -> {
      Optional<WeatherEntity> weatherEntity = weatherEntityList.stream()
          .filter(entity -> Objects.equals(entity.getCityId(), openWeatherResponseDto.getId()))
          .findFirst();
      if (weatherEntity.isPresent()) {
        weatherEntity.get().setTemperature(openWeatherResponseDto.getMain().getTemp());
        weatherEntity.get().setMaxTemperature(openWeatherResponseDto.getMain().getTemp_max());
        weatherEntity.get().setMinTemperature(openWeatherResponseDto.getMain().getTemp_min());
        weatherEntity.get().setHumidity(openWeatherResponseDto.getMain().getHumidity());
      }
    });
  }

  private Mono<OpenWeatherResponseDto> getWeather(Long cityId, String units, String lang) {
    return openWeatherClient.get().uri(WeatherConstants.GET_CITY_WEATHER_BY_ID, cityId, units, lang).retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(OpenWeatherApiCallException::new))
        .bodyToMono(OpenWeatherResponseDto.class).switchIfEmpty(Mono.error(new OpenWeatherApiCallException()));
  }

  public Flux<OpenWeatherResponseDto> getWeatherForAllCitiesById(List<Long> cityIds,
      Optional<String> units,
      Optional<String> languageCode) {
    return Flux.fromIterable(cityIds)
        .flatMap(cityId -> getWeather(cityId, units.orElse("metric"), languageCode.orElse("en")));
  }
}
