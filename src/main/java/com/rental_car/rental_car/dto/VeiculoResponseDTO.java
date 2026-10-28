package com.rental_car.rental_car.dto;

import com.rental_car.rental_car.model.StatusVeiculo;
import com.rental_car.rental_car.model.Veiculo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class VeiculoResponseDTO {

    private int id;
    private String marca;
    private String modelo;
    private String placa;
    private StatusVeiculo statusVeiculo;
    private BigDecimal valorDiaria;

    public static VeiculoResponseDTO fromEntity(Veiculo veiculo){
        VeiculoResponseDTO dto = new VeiculoResponseDTO();
        dto.setId(veiculo.getId());
        dto.setMarca(veiculo.getMarca());
        dto.setModelo(veiculo.getModelo());
        dto.setPlaca(veiculo.getPlaca());
        dto.setStatusVeiculo(veiculo.getStatusVeiculo());
        dto.setValorDiaria(veiculo.getValorDiaria());
        return dto;
    }


}
