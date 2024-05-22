package com.example.weather.data.dto;

import com.example.weather.data.dto.openweather.CoordDto;
import lombok.Data;

@Data
public class CityDto {

  private Long id;
  private String name;
  private String state;
  private String country;
  private CoordDto coord;

}
