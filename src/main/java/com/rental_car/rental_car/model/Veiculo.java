package com.rental_car.rental_car.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String placa;
    private String modelo;
    private BigDecimal valorDiaria;
    private String marca;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo statusVeiculo;

}
