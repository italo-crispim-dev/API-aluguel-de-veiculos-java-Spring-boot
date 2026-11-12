package com.rental_car.rental_car.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

//Comando de criação equivalente em SQL
/* create table tb_cliente (
        id integer not null auto_increment,
        email varchar(255) not null unique,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB
*/

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Aluguel> alugueis;

}
