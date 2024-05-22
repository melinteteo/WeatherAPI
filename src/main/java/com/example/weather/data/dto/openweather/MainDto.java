package com.example.weather.data.dto.openweather;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class MainDto {

  private double temp;
  private int pressure;
  private int humidity;
  private double temp_min;
  private double temp_max;
}
