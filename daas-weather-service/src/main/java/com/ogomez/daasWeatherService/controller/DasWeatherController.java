package com.ogomez.daasWeatherService.controller;

import com.ogomez.daasWeatherService.dto.WeatherEntityDto;
import com.ogomez.daasWeatherService.service.DasWeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(value = "EOS Weather DAS Service", produces = MediaType.APPLICATION_JSON_VALUE)
public class DasWeatherController {

    private final DasWeatherService service;


    @PostMapping("/weatherAudit")
    @ApiOperation("Save a forecast of a City in audit table")
    public ResponseEntity<List<WeatherEntityDto>> saveQuestion(@ApiParam("request body")
                                                                        @RequestBody List<WeatherEntityDto> weatherEntityDtos)
            throws Exception {
        List<WeatherEntityDto> result = service.saveWeatherAudit(weatherEntityDtos);
        return ResponseEntity.created(new URI("/api/v1/wheatherAudit"))
                .body(result);
    }

    @GetMapping("/weatherAudit/city({city}/prediction/{prediction}")
    @ApiOperation("Get forecast from audit table filter by city and type of prediction (sunny, storm,..)")
    public ResponseEntity <List<WeatherEntityDto>> gethistoric(@PathVariable String city,
                                                                   @PathVariable String prediction)
    throws Exception{

        List<WeatherEntityDto> result = service.getHistoricByCityPrediction(city,prediction);

        return ResponseEntity.ok(result);
    }

    public DasWeatherController(DasWeatherService service) {
        this.service = service;
    }
}
