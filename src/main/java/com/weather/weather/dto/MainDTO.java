package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainDTO {
    private double temp;
    private int pressure;
    private int humidity;
    private double temp_min;
    private double temp_max;
}
