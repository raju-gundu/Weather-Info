package com.weather.weather.repositories;

import com.weather.weather.models.WeatherInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeatherInfoRepository extends JpaRepository<WeatherInfo, Integer> {
    Optional<WeatherInfo> findByPincodeAndDate(long pincode, LocalDate date);
}
