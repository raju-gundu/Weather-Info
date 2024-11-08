package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherDTO {
    private long id;
    private String main;
    private String description;
    private String icon;
}
