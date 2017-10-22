package com.ogomez.daasWeatherService.dao;

import com.ogomez.daasWeatherService.domain.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherEntity,Long>{

    List<WeatherEntity> findByDesCityLikeIgnoreCaseAndDesTextLikeIgnoreCase(String city, String text);
}
