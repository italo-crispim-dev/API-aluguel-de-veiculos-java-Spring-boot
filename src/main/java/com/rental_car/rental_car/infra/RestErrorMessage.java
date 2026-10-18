package com.rental_car.rental_car.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class RestErrorMessage {
    private HttpStatus status;
    private String message;
}
