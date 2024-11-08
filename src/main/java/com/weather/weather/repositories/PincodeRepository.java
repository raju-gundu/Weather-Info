package com.weather.weather.repositories;

import com.weather.weather.models.Pincode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PincodeRepository extends JpaRepository<Pincode, Long> {
    Optional<Pincode> findByPincode(long pincode);
    Pincode save(Pincode pincode);
}
