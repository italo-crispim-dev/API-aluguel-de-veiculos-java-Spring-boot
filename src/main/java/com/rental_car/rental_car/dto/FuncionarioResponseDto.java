package com.rental_car.rental_car.dto;

import com.rental_car.rental_car.model.Funcionario;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FuncionarioResponseDto {

    private int id;
    private String nome;
    private String email;
    private BigDecimal salario;

    public static FuncionarioResponseDto fromEntity(Funcionario funcionario){
        FuncionarioResponseDto dto = new FuncionarioResponseDto();

        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setSalario(funcionario.getSalario());

        return dto;

    }
}