package com.example.weather.service.openweather;

import com.example.weather.data.entity.WeatherEntity;
import java.util.List;
import java.util.Optional;

public interface OpenWeatherService {

  void updateWeatherEntityList(List<WeatherEntity> listOfFirst100WeatherByCityEntity,
      Optional<String> units, Optional<String> languageCode);
}
