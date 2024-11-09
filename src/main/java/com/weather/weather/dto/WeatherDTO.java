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

    public WeatherDTO(long id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
}
