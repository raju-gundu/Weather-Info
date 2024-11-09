package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordDTO {
    private double lon;
    private double lat;

    public CoordDTO(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
