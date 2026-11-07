package com.rental_car.rental_car.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FuncionarioRequestDto {

    @NotBlank(message = "O nome do funcionario é obrigatorio")
    @Size(min = 3, message = "O nome do funcionario deve ter no minimo 3 caracteres")
    @Size(max = 100, message = "O nome do funcionario não pode ultrapassar 100 caracteres")
    private String nome;

    @NotBlank(message = "O email do funcionario é obrigatorio")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatoria")
    @Size(min = 8, message = "A senha deve ter no minimo 8 caracteres")
    private String senha;

    @NotNull(message = "O salario é obrigatorio")
    @Positive(message = "O salario deve ser maior que 0")
    private BigDecimal salario;


}
