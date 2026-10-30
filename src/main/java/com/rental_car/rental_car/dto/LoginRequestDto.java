package com.rental_car.rental_car.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String senha;
}
