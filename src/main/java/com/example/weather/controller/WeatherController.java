package com.example.weather.controller;

import com.example.weather.data.dto.WeatherDto;
import com.example.weather.service.weather.WeatherService;
import com.example.weather.utils.WeatherConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@Slf4j
@RequestMapping("/weather")
@RequiredArgsConstructor
@EnableWebMvc
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping(WeatherConstants.GET_ALL_CITIES)
  @Operation(responses = {
      @ApiResponse(
          responseCode = "200",
          content = @Content(examples = {
              @ExampleObject(name = "cityWeather", summary = "Retrieves the weather for 20 out of 100 cities.",
                  description = "Retrieves the temperature, humidity, maximum"
                                + " and minimum temperature for 20 out of 100 cities.",
                  value = WeatherConstants.GET_200_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(
          responseCode = "400",
          content = @Content(examples = {
              @ExampleObject(name = "badRequest", summary = "Returns a bad request message",
                  description = "Returns a bad request message",
                  value = WeatherConstants.GET_400_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(
          responseCode = "500",
          content = @Content(examples = {
              @ExampleObject(name = "internalServiceError", summary = "Returns an internal service error message",
                  description = "Returns an internal service error message",
                  value = WeatherConstants.GET_500_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  public ResponseEntity<Page<WeatherDto>> getWeatherPageForAllCities(
      @Parameter(description = "page to fetch") @RequestParam Optional<Integer> page,
      @Parameter(description = "value to sort by") @RequestParam Optional<String> sortBy) {
    log.info("getting the weather for all cities");
    return weatherService.getWeatherPageForAllCities(page, sortBy);
  }

  @DeleteMapping(WeatherConstants.DELETE_ALL_CITIES)
  @Operation(responses = {
      @ApiResponse(
          responseCode = "200",
          content = @Content(examples = {
              @ExampleObject(name = "deleteCitiesWeather", summary = "Deletes the weather for all cities",
                  description = "Deletes the weather for all cities",
                  value = "Success")},
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(
          responseCode = "500",
          content = @Content(examples = {
              @ExampleObject(name = "internalServiceError", summary = "Returns an internal service error message",
                  description = "Returns an internal service error message",
                  value = WeatherConstants.GET_500_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  public ResponseEntity<String> deleteAllCities() {
    log.info("deleting the weather for all cities");
    return weatherService.removeAll();
  }

  @GetMapping(WeatherConstants.REFRESH_ALL_CITIES)
  @Operation(responses = {
      @ApiResponse(
          responseCode = "200",
          content = @Content(examples = {
              @ExampleObject(name = "refreshCitiesWeather", summary = "Refreshes the weather for all cities.",
                  description = "Refreshes the weather for all cities.",
                  value = WeatherConstants.GET_200_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(
          responseCode = "400",
          content = @Content(examples = {
              @ExampleObject(name = "badRequest", summary = "Returns a bad request message",
                  description = "Returns a bad request message",
                  value = WeatherConstants.GET_400_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE)),
      @ApiResponse(
          responseCode = "500",
          content = @Content(examples = {
              @ExampleObject(name = "internalServiceError", summary = "Returns an internal service error message",
                  description = "Returns an internal service error message",
                  value = WeatherConstants.GET_500_EXAMPLE_JSON)},
              mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  public ResponseEntity<String> refreshWeatherAllCities(
      @Parameter(description = "units to display") @RequestParam Optional<String> units,
      @Parameter(description = "code for language to display in") @RequestParam Optional<String> languageCode) {
    log.info("refreshing the weather for all cities");
    return weatherService.refreshWeather(units, languageCode);
  }
}
