package com.weather.weather.controller;

import java.time.LocalDate;

import com.weather.weather.controllers.WeatherController;
import com.weather.weather.models.WeatherInfo;
import com.weather.weather.services.WeatherInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class ControllerMockMvcTests {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private WeatherInfoService weatherService;
    @InjectMocks
    private WeatherController weatherInfoController;

    @BeforeEach
    public void setUp(){
        mockMvc=MockMvcBuilders.standaloneSetup(weatherInfoController).build();
    }

    @Test
    public void testgetWeatherInfo() throws Exception{
        Integer pincode =411014 ;
        LocalDate for_date = LocalDate.parse("2024-11-08");
        WeatherInfo expectedWeatherInfo=new WeatherInfo();
        expectedWeatherInfo.setPincode(pincode);
        expectedWeatherInfo.setPlace("lnslkv");
        expectedWeatherInfo.setDate(for_date);
        expectedWeatherInfo.setTemperature(43.0);
        expectedWeatherInfo.setHumidity(100);
        expectedWeatherInfo.setPressure(960);
        expectedWeatherInfo.setWindSpeed(30.5);
        expectedWeatherInfo.setDescription("kdmvklvm");
        Mockito.when(weatherService.getWeatherInfo(pincode,for_date)).thenReturn(expectedWeatherInfo);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/weather")
                        .param("pincode",pincode.toString())
                        .param("for_date",for_date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath(".pincode").value(pincode))
                .andExpect(MockMvcResultMatchers.jsonPath(".temperature").value(expectedWeatherInfo.getTemperature()))
                .andExpect(MockMvcResultMatchers.jsonPath(".humidity").value(expectedWeatherInfo.getHumidity()))
                .andExpect(MockMvcResultMatchers.jsonPath(".pressure").value(expectedWeatherInfo.getPressure()))
                .andExpect(MockMvcResultMatchers.jsonPath(".windSpeed").value(expectedWeatherInfo.getWindSpeed()))
                .andExpect(MockMvcResultMatchers.jsonPath(".description").value(expectedWeatherInfo.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath(".place").value(expectedWeatherInfo.getPlace()));

    }


}
