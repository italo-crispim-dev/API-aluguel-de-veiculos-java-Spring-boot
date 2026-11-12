package com.rental_car.rental_car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

//Comando de criação equivalente em SQL
/* create table tb_veiculo (
        id integer not null auto_increment,
        marca varchar(255) not null,
        modelo varchar(255) not null,
        placa varchar(255) not null unique,
        valor_diaria decimal(38,2) not null,
        status_veiculo enum ('ALUGADO','DISPONIVEL','MANUNTENCAO') not null,
        primary key (id)
    ) engine=InnoDB
*/

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

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(name = "valor_diaria", nullable = false)
    private BigDecimal valorDiaria;

    @Column(nullable = false)
    private String marca;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_veiculo", nullable = false)
    private StatusVeiculo statusVeiculo;

    @OneToMany(mappedBy = "veiculo")
    @JsonIgnore
    private List<Aluguel> aluguels;

    @OneToMany(mappedBy = "veiculo")
    @JsonIgnore
    private List<Manutencao> manutencaoes;

}
