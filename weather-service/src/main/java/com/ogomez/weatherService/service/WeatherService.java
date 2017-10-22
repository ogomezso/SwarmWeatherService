package com.ogomez.weatherService.service;


import com.ogomez.weatherService.dto.WeatherResponseDto;
import org.springframework.web.client.HttpClientErrorException;

public interface WeatherService {

    WeatherResponseDto getweatherBycity(String City) throws HttpClientErrorException;
}
