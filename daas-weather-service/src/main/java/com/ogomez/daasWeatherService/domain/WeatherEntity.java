package com.ogomez.daasWeatherService.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "weather_audit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_audit")
    private Long auditId;

    @Column(name="des_city")
    private String desCity;

    @Column(name="des_date")
    private String desDate;

    @Column(name="des_day")
    private String desDay;

    @Column(name="des_high")
    private String desHigh;

    @Column(name="des_low")
    private String desLow;


    @Column(name="des_text")
    private String desText;

}
