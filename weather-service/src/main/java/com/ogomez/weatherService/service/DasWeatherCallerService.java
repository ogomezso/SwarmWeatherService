package com.ogomez.weatherService.service;

import com.ogomez.weatherService.dto.WeatherEntityDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface DasWeatherCallerService {

    CompletableFuture<List<WeatherEntityDto>> saveWeatherEntities(List<WeatherEntityDto> entities);
    List<WeatherEntityDto> getHistoric(String city, String text);

}
