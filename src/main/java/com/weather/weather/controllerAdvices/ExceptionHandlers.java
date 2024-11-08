package com.weather.weather.controllerAdvices;

import com.weather.weather.dto.ExceptionDTO;
import com.weather.weather.exceptions.PincodeNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(PincodeNotExistsException.class)
    public ResponseEntity<ExceptionDTO> handlePincodeNotExistsException(PincodeNotExistsException e) {
        ExceptionDTO dto = new ExceptionDTO();
        dto.setMessage(e.getMessage());
        dto.setDetail("Please enter a valid pincode");
        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }

}
