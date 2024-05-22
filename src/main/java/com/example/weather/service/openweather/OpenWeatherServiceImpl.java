package com.example.weather.service.openweather;

import com.example.weather.data.dto.openweather.OpenWeatherResponseDto;
import com.example.weather.data.entity.WeatherEntity;
import com.example.weather.exception.OpenWeatherApiCallException;
import com.example.weather.utils.WeatherConstants;
import java.util.ArrayList;
import java.util.List;
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
  public List<WeatherEntity> getWeatherForFirst100Cities(List<WeatherEntity> listOfFirst100WeatherByCityEntity,
      Optional<String> units, Optional<String> languageCode) {
    List<WeatherEntity> weatherEntityList = new ArrayList<>();
    Iterable<OpenWeatherResponseDto> openWeatherResponses;

    openWeatherResponses = getWeatherForAllCities(listOfFirst100WeatherByCityEntity, units, languageCode).toIterable();

    openWeatherResponses.forEach(openWeatherResponseDto -> weatherEntityList.add(
        WeatherEntity.builder().cityId(openWeatherResponseDto.getId()).cityName(openWeatherResponseDto.getName())
            .temperature(openWeatherResponseDto.getMain().getTemp())
            .maxTemperature(openWeatherResponseDto.getMain().getTemp_max())
            .minTemperature(openWeatherResponseDto.getMain().getTemp_min())
            .humidity(openWeatherResponseDto.getMain().getHumidity()).build()));

    return weatherEntityList;
  }

  public Mono<OpenWeatherResponseDto> getWeather(Long cityId, String units, String lang) {
    return openWeatherClient.get().uri(WeatherConstants.GET_CITY_WEATHER_BY_ID, cityId, units, lang).retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(OpenWeatherApiCallException::new))
        .bodyToMono(OpenWeatherResponseDto.class).switchIfEmpty(Mono.error(new OpenWeatherApiCallException()));
  }

  public Flux<OpenWeatherResponseDto> getWeatherForAllCities(List<WeatherEntity> weatherEntities, Optional<String> units,
      Optional<String> languageCode) {
    return Flux.fromIterable(weatherEntities)
        .flatMap(weather -> getWeather(weather.getCityId(), units.orElse("metric"), languageCode.orElse("en")));
  }
}
