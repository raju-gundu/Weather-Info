package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysDTO {
    private int type;
    private long id;
    private double message;
    private String country;
    private long sunrise;
    private long sunset;
}
