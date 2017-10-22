package com.ogomez.weatherService.service.impl;

import com.ogomez.weatherService.config.WeatherConfig;
import com.ogomez.weatherService.dto.CitiesEnum;
import com.ogomez.weatherService.dto.WeatherEntityDto;
import com.ogomez.weatherService.dto.WeatherResponseDto;
import com.ogomez.weatherService.service.WeatherService;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DasWeatherCallerServiceImpl callerService;

    @Autowired
    private WeatherConfig config;

    private CitiesEnum cities;

    @Override
    public WeatherResponseDto getweatherBycity(String city) throws HttpClientErrorException {

        WeatherResponseDto ret = null;
        List<WeatherEntityDto> entityDto = new ArrayList<>();

        if (EnumUtils.isValidEnum(CitiesEnum.class,city)) {

           String uriString = config.getBaseUrl()+config.getPath()+"?q="
                   +config.getQuery()+CitiesEnum.valueOf(city).getCode()+"&format="+config.getFormat();

            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uriString);

            ret = restTemplate.getForObject(builder.build(true).toUri(), WeatherResponseDto.class);

            String cityValue = ret.getQuery().getResults().getChannel().getLocation().getCity();
            ret.getQuery().getResults().getChannel().getItem().getForecast().forEach(
                    f-> entityDto.add(WeatherEntityDto.builder()
                            .desCity(cityValue)
                            .desDate(f.getDate())
                            .desDay(f.getDay())
                            .desHigh(f.getHigh())
                            .desLow(f.getLow())
                            .desText(f.getText())
                            .build())
            );

            callerService.saveWeatherEntities(entityDto);
        }

        return ret;
    }
}
