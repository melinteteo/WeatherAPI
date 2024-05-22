package com.example.weather.utils;

public final class WeatherConstants {

  private WeatherConstants() {

  }

  public static final String GET_ALL_CITIES = "get/all";
  public static final String DELETE_ALL_CITIES = "/delete/all";
  public static final String REFRESH_ALL_CITIES = "refresh/all";
  public static final String OPEN_WEATHER_HOST = "https://api.openweathermap.org";
  public static final String GET_CITY_WEATHER_BY_ID =
      "/data/2.5/weather?id={cityId}&appid=936744e142bae3c64b4d137b4ad97a40&units={metric}&lang={lang}";
  public static final String GET_200_EXAMPLE_JSON = """
      {
          "content": [
              {
                  "cityName": "Berlin",
                  "temperature": 9.5,
                  "humidity": 80.0,
                  "minTemperature": 9.5,
                  "maxTemperature": 9.5
              }
          ]
      }""";

  public static final String GET_400_EXAMPLE_JSON = """
      {
           "httpCode": 400,
           "message": "Make sure that you have input the correct data type",
           "timestamp": 1716394388.875877000
      }""";

  public static final String GET_500_EXAMPLE_JSON = """
      {
          "timestamp":1716393172755,
          "status":500,
          "error":"Internal Server Error",
          "path":"/weather/get/all"
          
      }""";
}
