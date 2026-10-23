package com.rental_car.rental_car.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoRequestDTO {

    @NotBlank(message = "A placa não pode ser vazia")
    @Pattern(
            regexp = "^[A-Z]{3}[0-9][A-Z0-9][0-9]{2}$",
            message = "A placa deve seguir o Modelo do Mercosul, como ABC1D23 ou o modelo antigo como ABC1234"
    )
    private String placa;

    @NotBlank(message = "O modelo não pode ser vazio")
    private String modelo;

    @Positive(message = "A diaria deve ser maior que 0")
    private double valor_diaria;

    @NotBlank(message = "A marca não pode ser vazia")
    private String marca;

}
