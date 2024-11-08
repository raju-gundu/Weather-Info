package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherApiDTO {
    private CoordDTO coord;
    private List<WeatherDTO> weather;
    private String base;
    private MainDTO main;
    private long visiblity;
    private WindDTO wind;
    private CloudsDTO clouds;
    private long dt;
    private SysDTO sys;
    private long id;
    private String name;
    private long cod;
}
