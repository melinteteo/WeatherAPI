package com.example.weather.service.weather;

import com.example.weather.data.dto.WeatherDto;
import java.io.IOException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface WeatherService {


  void initWeather() throws IOException;

  ResponseEntity<Page<WeatherDto>> getWeatherPageForAllCities(Optional<Integer> page, Optional<String> sortBy);

  ResponseEntity<String> removeAll();

  ResponseEntity<String> refreshWeather(Optional<String> units, Optional<String> languageCode);
}
