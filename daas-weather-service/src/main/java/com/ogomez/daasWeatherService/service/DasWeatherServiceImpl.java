package com.ogomez.daasWeatherService.service;

import com.ogomez.daasWeatherService.dao.WeatherRepository;
import com.ogomez.daasWeatherService.domain.WeatherEntity;
import com.ogomez.daasWeatherService.dto.WeatherEntityDto;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DasWeatherServiceImpl implements DasWeatherService {

    private final WeatherRepository weatherRepository;

    private final DozerBeanMapper dozerBeanMapper;


    @Override
    public List<WeatherEntityDto> saveWeatherAudit(List<WeatherEntityDto> req) throws Exception {

        List<WeatherEntity> entity = new ArrayList<>();

        req.forEach(r-> entity.add(dozerBeanMapper.map(r,WeatherEntity.class)));
        weatherRepository.save(entity);

        return req;
    }

    @Override
    public List<WeatherEntityDto> getHistoricByCityPrediction(String city,String text) throws Exception{

        List<WeatherEntity> entity = weatherRepository.findByDesCityLikeIgnoreCaseAndDesTextLikeIgnoreCase(city,"%"+text+"%");
        Optional<List<WeatherEntityDto>> result = Optional.ofNullable(
                entity.stream()
                        .map(e -> this.dozerBeanMapper.map(e, WeatherEntityDto.class))
                        .collect(Collectors.toCollection(ArrayList::new)));
        return result.orElse(new ArrayList<>());
    }

    public DasWeatherServiceImpl(WeatherRepository weatherRepository, DozerBeanMapper dozerBeanMapper) {
        this.weatherRepository = weatherRepository;
        this.dozerBeanMapper = dozerBeanMapper;
    }
}
