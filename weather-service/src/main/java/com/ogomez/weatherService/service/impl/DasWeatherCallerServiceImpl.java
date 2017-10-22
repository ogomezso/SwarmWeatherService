package com.ogomez.weatherService.service.impl;

import com.ogomez.weatherService.config.WeatherConfig;
import com.ogomez.weatherService.dto.WeatherEntityDto;
import com.ogomez.weatherService.service.DasWeatherCallerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DasWeatherCallerServiceImpl implements DasWeatherCallerService {


    private final RestTemplate restTemplate;
    private final WeatherConfig weatherConfig;

    private static final String protocol = "http://";

    @Override
    public CompletableFuture<List<WeatherEntityDto>> saveWeatherEntities(List<WeatherEntityDto> entities) {

        return CompletableFuture.supplyAsync(() -> retrieveSaveResponse(entities));
    }

    private List<WeatherEntityDto> retrieveSaveResponse(List<WeatherEntityDto> entities){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        HttpEntity<List<WeatherEntityDto>> request = new HttpEntity(entities,httpHeaders);

        ResponseEntity<WeatherEntityDto[]> result = restTemplate.exchange(
                protocol+weatherConfig.getDasWeatherServiceId()
                        +':'+weatherConfig.getDasWeatherServicePort()
                        +weatherConfig.getSaveResponse(),
                HttpMethod.POST,
                request,
                WeatherEntityDto[].class);
        return Arrays.asList(result.getBody());
    }

    @Override
    public List<WeatherEntityDto> getHistoric(String city, String text) throws HttpClientErrorException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        HttpEntity<Object> request = new HttpEntity(null, httpHeaders);

        String[] parameters ={city,text};

        ResponseEntity<WeatherEntityDto[]> result = restTemplate.exchange(
                protocol+weatherConfig.getDasWeatherServiceId()
                        +':'+weatherConfig.getDasWeatherServicePort()
                        +weatherConfig.getGetHistoric(),
                HttpMethod.GET,
                request,
                WeatherEntityDto[].class,
                parameters);

        return Arrays.asList(result.getBody());
    }

    public DasWeatherCallerServiceImpl(RestTemplate restTemplate, WeatherConfig weatherConfig) {
        this.restTemplate = restTemplate;
        this.weatherConfig = weatherConfig;
    }
}
