package com.weather.weather.controllers;

import com.weather.weather.models.WeatherInfo;
import com.weather.weather.services.WeatherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private WeatherInfoService weatherInfoService;

    @Autowired
    public WeatherController(WeatherInfoService weatherInfoService){
        this.weatherInfoService = weatherInfoService;
    }

    @GetMapping()
    public ResponseEntity<WeatherInfo> getWeatherInfo(@RequestParam long pincode,
                                                      @RequestParam @DateTimeFormat(pattern = "yyyy-mm-dd")LocalDate for_date) throws Exception {
        WeatherInfo weatherInfo = weatherInfoService.getWeatherInfo(pincode,for_date);
        return new ResponseEntity<>(weatherInfo, HttpStatus.OK);
    }
}
