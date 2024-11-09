package com.weather.weather.controller;

import com.weather.weather.controllers.WeatherController;
import com.weather.weather.models.WeatherInfo;
import com.weather.weather.services.WeatherInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class WeatherInfoControllerTest {
    @Mock
    private WeatherInfoService weatherInfoService;

    @InjectMocks
    private WeatherController weatherController;

    private WeatherInfo weatherInfo;

    @BeforeEach
    public void setUp(){
        this.weatherInfo = new WeatherInfo();
        weatherInfo.setPincode(00000);
        weatherInfo.setPlace("mdionv");
        LocalDate for_date = LocalDate.parse("2024-11-08");
        weatherInfo.setDate(for_date);
        weatherInfo.setTemperature(95.6);
        weatherInfo.setHumidity(35);
        weatherInfo.setPressure(9600);
        weatherInfo.setWindSpeed(0.0);
        weatherInfo.setDescription("aenvkjvnmvoapjvewi");

    }

    @Test
    void testGetWeatherInfo() throws Exception{
        Mockito.when(weatherInfoService.getWeatherInfo(00000,LocalDate.parse("2024-11-08"))).thenReturn(weatherInfo);
        ResponseEntity<WeatherInfo> actualWeatherInfo = weatherController.getWeatherInfo(00000,LocalDate.parse("2024-11-08"));

        Assertions.assertEquals(HttpStatus.OK,actualWeatherInfo.getStatusCode());
        Assertions.assertEquals(weatherInfo.getTemperature(),actualWeatherInfo.getBody().getTemperature());
        Assertions.assertEquals(weatherInfo.getWindSpeed(),actualWeatherInfo.getBody().getWindSpeed());
        verify(weatherInfoService,Mockito.times(1)).getWeatherInfo(00000,LocalDate.parse("2024-11-08"));
    }
}
