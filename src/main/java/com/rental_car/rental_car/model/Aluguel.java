package com.rental_car.rental_car.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


//Comando de criação equivalente em SQL
/*[ create table tb_aluguel (
        id integer not null auto_increment,
        id_cliente integer,
        id_funcionario integer,
        id_veiculo integer,
        data_inicio date not null,
        data_termino date not null,
        valor_aluguel decimal(38,2) not null,
        status_aluguel enum ('AGENDADO','ATIVO','CANCELADO','FINALIZADO') not null,
        primary key (id),
        constraint fk_aluguel_cliente foreign key (id_cliente) references tb_cliente (id),
        constraint fk_aluguel_funcionario foreign key (id_funcionario) references tb_funcionario (id),
        constraint fk_aluguel_veiculo foreign key (id_veiculo) references tb_veiculo (id)
    ) engine=InnoDB
*/

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tb_aluguel")
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    @Column(name = "valor_aluguel", nullable = false)
    private BigDecimal valorAluguel;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_termino", nullable = false)
    private LocalDate dataTermino;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_aluguel", nullable = false)
    private StatusAluguel statusAluguel;

}
