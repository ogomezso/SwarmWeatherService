package com.ogomez.weatherService.controller;

import com.ogomez.weatherService.dto.WeatherEntityDto;
import com.ogomez.weatherService.dto.WeatherResponseDto;
import com.ogomez.weatherService.service.DasWeatherCallerService;
import com.ogomez.weatherService.service.WeatherService;
import com.ogomez.weatherService.util.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@Api(value = "EOS Weather Service", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherRestController {

    private final WeatherService weatherService;
    private final DasWeatherCallerService dasWeatherCallerService;

    @GetMapping("weather/{city}")
    @ApiOperation(value = "Given a city Get weather prediction for next week")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Standard response for successful HTTP requests"),
            @ApiResponse(code = 404, message = "User does not exist or is missing city")
    })
    public ResponseEntity<WeatherResponseDto> getWeatherByCity(
        @ApiParam(required = true, value = "City we want to request weather prediction")
        @PathVariable String city) throws HttpClientErrorException {

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(weatherService.getweatherBycity(city)));
    }

    @GetMapping("weather/history/city/{city}/prediction/{prediction}")
    @ApiOperation(value = "Given a city and the text of a prediction get weather prediction stored in audit database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Standard response for successful HTTP requests"),
            @ApiResponse(code = 404, message = "User does not exist or is missing city")
    })
    public ResponseEntity<WeatherEntityDto> getHistory(
            @ApiParam(required = true, value = "City we want to request historic weather prediction")
            @PathVariable String city,
            @ApiParam(required = true, value = "Type of prediction we want to filter (sunny,cloudy,rain,..) ")
            @PathVariable String prediction
    ) throws HttpClientErrorException {

        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dasWeatherCallerService.getHistoric(city, prediction)));
    }

    public WeatherRestController(WeatherService weatherService, DasWeatherCallerService dasWeatherCallerService) {
        this.weatherService = weatherService;
        this.dasWeatherCallerService = dasWeatherCallerService;
    }
}
