package com.example.weather.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherDto {
  @JsonIgnore
  private Long id;
  private String cityName;
  @JsonIgnore
  private Long cityId;
  private double temperature;
  private double humidity;
  private double minTemperature;
  private double maxTemperature;
}
