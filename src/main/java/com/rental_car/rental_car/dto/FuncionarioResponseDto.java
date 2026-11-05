package com.rental_car.rental_car.dto;

import com.rental_car.rental_car.model.Cargo;
import com.rental_car.rental_car.model.Funcionario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FuncionarioResponseDto {

    private int id;
    private String nome;
    private String email;
    private double salario;
    private Cargo cargo;

    public static FuncionarioResponseDto fromEntity(Funcionario funcionario){
        FuncionarioResponseDto dto = new FuncionarioResponseDto();

        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setCargo(funcionario.getCargo());
        dto.setSalario(funcionario.getSalario());

        return dto;

    }
}