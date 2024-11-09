package com.weather.weather.service;

import com.weather.weather.dto.CoordDTO;
import com.weather.weather.dto.GeoCodingAPIResponse;
import com.weather.weather.dto.WeatherApiDTO;
import com.weather.weather.dto.WeatherDTO;
import com.weather.weather.exceptions.PincodeNotExistsException;
import com.weather.weather.models.Pincode;
import com.weather.weather.models.WeatherInfo;
import com.weather.weather.repositories.PincodeRepository;
import com.weather.weather.repositories.WeatherInfoRepository;
import com.weather.weather.services.WeatherInfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
public class WeatherInfoServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PincodeRepository pincodeRepository;

    @Mock
    private WeatherInfoRepository weatherInfoRepository;

    @InjectMocks
    private WeatherInfoService weatherInfoService;

    @Test
    void testGetPincode() throws PincodeNotExistsException {
        GeoCodingAPIResponse mockGeoCodingAPIResponse = new GeoCodingAPIResponse();
        mockGeoCodingAPIResponse.setPincode("411014");
        mockGeoCodingAPIResponse.setLat(18.5685);
        mockGeoCodingAPIResponse.setLon(73.9158);
        mockGeoCodingAPIResponse.setName("Pune");

        ResponseEntity<GeoCodingAPIResponse> mockResponseEntity = ResponseEntity.ok(mockGeoCodingAPIResponse);
        Mockito.when(restTemplate.getForEntity(anyString(), ArgumentMatchers.eq(GeoCodingAPIResponse.class))).thenReturn(mockResponseEntity);

        Pincode pincode = weatherInfoService.getPincode(411014);

        Assertions.assertEquals(mockGeoCodingAPIResponse.getPincode(), Long.toString(pincode.getPincode()));
        Assertions.assertEquals(mockGeoCodingAPIResponse.getLat(), pincode.getLatitude());
        Assertions.assertEquals(mockGeoCodingAPIResponse.getLon(), pincode.getLongitude());
    }

    @Test
    void  testGetWeatherResponse() throws Exception {
        WeatherApiDTO weatherApiDTO = new WeatherApiDTO();
        weatherApiDTO.setCoord(new CoordDTO(73.9158,18.5685));
        List<WeatherDTO> temp=new ArrayList<>();
        temp.add(new WeatherDTO( 800, "Clear",  "clear sky",  "01d"));
        weatherApiDTO.setWeather(temp);
        Mockito.when(restTemplate.getForEntity(anyString(), ArgumentMatchers.eq(WeatherApiDTO.class))).thenReturn(new ResponseEntity<>(weatherApiDTO, HttpStatus.OK));
        WeatherApiDTO actualResponse=weatherInfoService.getWeatherResponse(18.5685, 73.9158, LocalDate.parse("2020-10-14"));
        Assertions.assertEquals(weatherApiDTO.getCoord().getLat(), actualResponse.getCoord().getLat());
        Assertions.assertEquals(weatherApiDTO.getCoord().getLon(), actualResponse.getCoord().getLon());
        Assertions.assertEquals(weatherApiDTO.getWeather().get(0).getMain(), actualResponse.getWeather().get(0).getMain());
    }

    @Test
    void testGetWeatherInfo() throws Exception {

        Pincode mockPincode = new Pincode(411014, 18.5685, 73.9158);


        WeatherInfo weatherInfomock=new WeatherInfo();
        weatherInfomock.setPincode(411014);
        weatherInfomock.setDate(LocalDate.parse("2020-10-15"));
        weatherInfomock.setPlace("Viman Nagar");
        weatherInfomock.setDescription("clear sky");


        WeatherApiDTO mockResponse=new WeatherApiDTO();
        mockResponse.setCoord(new CoordDTO(73.9158,18.5685));
        List<WeatherDTO> temp=new ArrayList<>();
        temp.add(new WeatherDTO( 800, "Clear",  "clear sky",  "01d"));
        mockResponse.setWeather(temp);

        Mockito.when(pincodeRepository.findById(411014L)).thenReturn(Optional.of(mockPincode));
        Mockito.when(weatherInfoRepository.findByPincodeAndDate(411014,LocalDate.parse("2020-10-15"))).thenReturn(Optional.of(weatherInfomock));
       Mockito.when(weatherInfoRepository.save(weatherInfomock)).thenReturn(weatherInfomock);
        Mockito.when(restTemplate.getForEntity(anyString(),ArgumentMatchers.eq(WeatherApiDTO.class))).thenReturn(ResponseEntity.ok(mockResponse));

        WeatherInfo weatherInfo = weatherInfoService.getWeatherInfo(411014, LocalDate.parse("2020-10-15"));
        Assertions.assertNotNull(weatherInfo);
        Assertions.assertEquals(weatherInfomock.getPincode(), weatherInfo.getPincode());
        Assertions.assertEquals(weatherInfomock.getDate(), weatherInfo.getDate());
        Assertions.assertEquals(weatherInfomock.getPlace(), weatherInfo.getPlace());
        Mockito.verify(weatherInfoRepository).findByPincodeAndDate(411014, LocalDate.parse("2020-10-15"));
        Mockito.verifyNoMoreInteractions(weatherInfoRepository);
    }





    }

