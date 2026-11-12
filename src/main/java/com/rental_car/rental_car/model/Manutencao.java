package com.rental_car.rental_car.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

//Comando de criação equivalente em SQL
/* create table tb_manutencao (
        id integer not null auto_increment,
        veiculo_id integer not null,
        descricao varchar(255) not null,
        valor decimal(38,2) not null,
        data_manuntencao date not null,
        status_manuntencao enum ('ATIVA','FINALIZADA') not null,
        primary key (id),
        constraint fk_mauntencao_veiculo foreign key (veiculo_id) references tb_veiculo (id)

    ) engine=InnoDB
*/

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "tb_manutencao")
public class Manutencao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "data_manuntencao", nullable = false)
    private LocalDate dataManutencao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_manuntencao", nullable = false)
    private StatusManutencao statusManutencao;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    private Veiculo veiculo;
}
