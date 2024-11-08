package com.weather.weather.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeoCodingAPIResponse {
    private String pincode;
    private String name;
    private double lat;
    private double lon;

}
