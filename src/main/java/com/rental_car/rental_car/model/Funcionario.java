package com.rental_car.rental_car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tb_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    private String nome;
    private String email;
    @JsonIgnore
    private String senha;
    private double salario;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @OneToMany(mappedBy = "funcionario")
    private List<Aluguel> alugueis;


}
