package com.ogomez.apiweatherservice.service.impl;

import com.ogomez.apiweatherservice.config.DependenciesConfig;
import com.ogomez.apiweatherservice.config.ServiceConfig;
import com.ogomez.apiweatherservice.dto.WeatherEntityDto;
import com.ogomez.apiweatherservice.dto.WeatherResponseDto;
import com.ogomez.apiweatherservice.service.ApiCallerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
class ApiCallerServiceImpl implements ApiCallerService {

    private final RestTemplate restTemplate;
    private final ServiceConfig serviceConfig;
    private final DependenciesConfig dependenciesConfig;
    private static String protocol = "http://";

    @Override
    public WeatherResponseDto getPrediction(String city)
             throws HttpClientErrorException{
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        HttpEntity<Object> request = new HttpEntity(null, httpHeaders);

        String[] parameters ={city};

        ResponseEntity<WeatherResponseDto> result = restTemplate.exchange(
                protocol
                        +dependenciesConfig.getWeatherServiceId()
                        +':'
                        +dependenciesConfig.getWeatherServicePort()
                        +serviceConfig.getGetPredictionUri(),
                HttpMethod.GET,
                request,
                WeatherResponseDto.class,
                parameters);

        return result.getBody();
    }

    @Override
    public List<WeatherEntityDto> getPredictionHistory(String city, String prediction)
            throws HttpClientErrorException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        HttpEntity<Object> request = new HttpEntity(null, httpHeaders);

        String[] parameters ={city,prediction};

        ResponseEntity<WeatherEntityDto[]> result = restTemplate.exchange(
                protocol
                        +dependenciesConfig.getWeatherServiceId()
                        +':'
                        +dependenciesConfig.getWeatherServicePort()
                        +serviceConfig.getGetHistoricUri(),
                HttpMethod.GET,
                request,
                WeatherEntityDto[].class,
                parameters);

        return Arrays.asList(result.getBody());
    }

    ApiCallerServiceImpl(RestTemplate restTemplate,
                         ServiceConfig serviceConfig,
                         DependenciesConfig dependenciesConfig) {

        this.restTemplate = restTemplate;
        this.serviceConfig = serviceConfig;
        this.dependenciesConfig = dependenciesConfig;
    }
}
