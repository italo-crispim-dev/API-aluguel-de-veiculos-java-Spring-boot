package com.rental_car.rental_car.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AluguelResquestDto {
    @NotNull(message = "O ID do cliente não pode ser vazio")
    private Integer idCliente;

    @NotNull(message = "O ID do funcionario não pode ser vazio")
    private Integer idFuncionario;

    @NotNull(message = "O ID do veiculo não pode ser vazio")
    private Integer idVeiculo;

    @NotNull(message = "A data de inicio não pode ser vazio")
    @FutureOrPresent(message = "A data não pode estar no passado")
    private LocalDate dataInicio;

    @NotNull(message = "A data de termino não pode ser vazio")
    @Future(message = "A data de termino deve ser no futuro")
    private LocalDate dataTermino;
}
