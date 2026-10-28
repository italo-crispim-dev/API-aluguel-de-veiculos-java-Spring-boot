package com.rental_car.rental_car.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AluguelUpdateDto {

    @NotNull(message = "A data de inicio não pode ser vazio")
    @FutureOrPresent(message = "A data não pode estar no passado")
    private LocalDate dataInicio;

    @NotNull(message = "A data de termino não pode ser vazio")
    @Future(message = "A data de termino deve ser no futuro")
    private LocalDate dataTermino;

}
