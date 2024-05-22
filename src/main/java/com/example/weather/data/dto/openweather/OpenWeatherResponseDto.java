package com.example.weather.data.dto.openweather;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Builder
public class OpenWeatherResponseDto {

  private CoordDto coord;
  private List<WeatherDto> weather;
  private String base;
  private MainDto main;
  private int visibility;
  private WindDto wind;
  private CloudsDto clouds;
  private int dt;
  private SysDto sys;
  private Long id;
  private String name;
  private int cod;

}
