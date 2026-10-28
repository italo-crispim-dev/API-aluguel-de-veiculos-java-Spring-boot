package com.rental_car.rental_car.dto;

import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.model.StatusAluguel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class AluguelResponseDto {

    private int id;
    private int idVeiculo;
    private String placaVeiculo;
    private String modeloVeiculo;
    private String marcaVeiculo;
    private int idCliente;
    private String nomeCliente;
    private int idFuncionario;
    private String nomeFuncionario;
    private BigDecimal valorAluguel;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private StatusAluguel statusAluguel;

    public static AluguelResponseDto fromEntity(Aluguel aluguel){
        AluguelResponseDto dto = new AluguelResponseDto();
        dto.setId(aluguel.getId());

        dto.setIdVeiculo(aluguel.getVeiculo().getId());
        dto.setPlacaVeiculo(aluguel.getVeiculo().getPlaca());
        dto.setModeloVeiculo(aluguel.getVeiculo().getModelo());
        dto.setMarcaVeiculo(aluguel.getVeiculo().getMarca());

        dto.setIdCliente(aluguel.getCliente().getId());
        dto.setNomeCliente(aluguel.getCliente().getNome());

        dto.setIdFuncionario(aluguel.getFuncionario().getId());
        dto.setNomeFuncionario(aluguel.getFuncionario().getNome());

        dto.setDataInicio(aluguel.getDataInicio());
        dto.setDataTermino(aluguel.getDataTermino());
        dto.setValorAluguel(aluguel.getValorAluguel());
        dto.setStatusAluguel(aluguel.getStatusAluguel());
        return dto;
    }

}
