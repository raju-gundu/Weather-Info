package com.weather.weather.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class WeatherInfo extends BaseModel{
    private String place;
    private LocalDate date;
    private String description;
    private long pincode;
    private double windSpeed;
    private double temperature;
    private int humidity;
    private int pressure;

}
