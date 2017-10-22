package com.ogomez.apiweatherservice.service;

import com.ogomez.apiweatherservice.dto.WeatherEntityDto;
import com.ogomez.apiweatherservice.dto.WeatherResponseDto;

import java.util.List;

public interface ApiCallerService {

    WeatherResponseDto getPrediction(String city);

    List<WeatherEntityDto> getPredictionHistory(String city, String prediction);
}
