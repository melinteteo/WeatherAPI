package com.example.weather.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity {

  @Id
  @Column(name = "ID", unique = true, nullable = false, precision = 15)
  @SequenceGenerator(name = "weather_entity_seq_generator", sequenceName = "weather_entity_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_entity_seq_generator")
  private Long id;

  @Column(name = "CITY")
  private String cityName;

  @NotNull
  @Column(name = "CITY_ID")
  private Long cityId;

  @NotNull
  @Column(name = "TEMPERATURE", precision = 15)
  private double temperature;

  @NotNull
  @Column(name = "HUMIDITY", precision = 15)
  private double humidity;

  @NotNull
  @Column(name = "MAXIMUM_TEMPERATURE", precision = 15)
  private double minTemperature;

  @NotNull
  @Column(name = "MINIMUM_TEMPERATURE", precision = 15)
  private double maxTemperature;
}
