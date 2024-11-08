package com.weather.weather.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pincode extends BaseModel{
    private long pincode;
    private double latitude;
    private double longitude;


}
