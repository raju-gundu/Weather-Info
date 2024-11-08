package com.weather.weather.services;

import com.weather.weather.dto.GeoCodingAPIResponse;
import com.weather.weather.dto.WeatherApiDTO;
import com.weather.weather.exceptions.PincodeNotExistsException;
import com.weather.weather.models.Pincode;
import com.weather.weather.models.WeatherInfo;
import com.weather.weather.repositories.PincodeRepository;
import com.weather.weather.repositories.WeatherInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class WeatherInfoService {

    @Value("${OPEN_WEATHER_MAP_API_KEY}")
    private String weatherAPIKey;
    private WeatherInfoRepository weatherInfoRepository;
    private PincodeRepository pincodeRepository;
    private RestTemplate restTemplate;

    @Autowired
    public WeatherInfoService(WeatherInfoRepository weatherInfoRepository, PincodeRepository pincodeRepository, RestTemplate restTemplate) {
        this.weatherInfoRepository = weatherInfoRepository;
        this.pincodeRepository = pincodeRepository;
        this.restTemplate = restTemplate;
    }

    private WeatherInfo convertWeatherApiDtoToWeatherInfo(WeatherApiDTO dto,long pincode,LocalDate date){
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setPincode(pincode);
        weatherInfo.setDate(date);
        weatherInfo.setDescription(dto.getWeather().get(0).getDescription());
        weatherInfo.setPlace(dto.getName());
        weatherInfo.setPressure(dto.getMain().getPressure());
        weatherInfo.setHumidity(dto.getMain().getHumidity());
        weatherInfo.setTemperature(dto.getMain().getTemp());
        weatherInfo.setWindSpeed(dto.getWind().getSpeed());
        return weatherInfo;

    }

    public Pincode getPincode(long pincode) throws PincodeNotExistsException {
        String pincodeUrl = "https://api.openweathermap.org/data/2.5/weather?zip="+pincode+",in&appid="+weatherAPIKey;
        ResponseEntity<GeoCodingAPIResponse> coords = restTemplate.getForEntity(pincodeUrl, GeoCodingAPIResponse.class);
        double latitude, longitude;
        if(coords.getStatusCode().is2xxSuccessful()){
             latitude = coords.getBody().getLat();
             longitude = coords.getBody().getLon();
        }
        else{
            throw new PincodeNotExistsException("Pincode "+ pincode + " is invalid");
        }
        Pincode p = new Pincode();
        p.setPincode(pincode);
        p.setLatitude(latitude);
        p.setLongitude(longitude);

        pincodeRepository.save(p);

        return p;
    }

    public WeatherApiDTO getWeatherResponse(double latitude, double longitude,LocalDate date) throws Exception {
        long currTime = date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
        String weatherUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid="
        + weatherAPIKey;
        ResponseEntity<WeatherApiDTO> weather = restTemplate.getForEntity(weatherUrl, WeatherApiDTO.class);
        if(weather.getStatusCode().isError()){
            throw new Exception(weather.getStatusCode().toString());
        }
        return weather.getBody();
    }

    public WeatherInfo getWeatherInfo(long pincode, LocalDate date) throws Exception {
        double latitude, longitude;
        Optional<WeatherInfo> optionalWeatherInfo = weatherInfoRepository.findByPincodeAndDate(pincode,date);
        if(optionalWeatherInfo.isPresent()){
            return optionalWeatherInfo.get();
        }

        Optional<Pincode> optionalPincode = pincodeRepository.findByPincode(pincode);
        if(optionalPincode.isPresent()){
            Pincode existingPincode = optionalPincode.get();
            latitude = existingPincode.getLatitude();
            longitude = existingPincode.getLongitude();
        }
        else{
            Pincode extractedPincode = getPincode(pincode);
            latitude = extractedPincode.getLatitude();
            longitude = extractedPincode.getLongitude();
        }

        WeatherApiDTO weatherApiResponse = getWeatherResponse(latitude,longitude,date);
        WeatherInfo weatherInfo = convertWeatherApiDtoToWeatherInfo(weatherApiResponse,pincode,date);
        return weatherInfo;
    }
}
