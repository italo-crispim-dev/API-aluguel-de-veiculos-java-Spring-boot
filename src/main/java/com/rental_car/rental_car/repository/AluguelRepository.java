package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.model.StatusAluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {

    //Lista todos os alugueis
    List<Aluguel> findByClienteId(Integer id);

    @Query("select coalesce(sum(a.valorAluguel)) from Aluguel a WHERE a.statusAluguel = :status and month(a.dataTermino) = :mes and YEAR(a.dataTermino) = :ano ")
    BigDecimal valorAluguelPorMes(
            @Param("status") StatusAluguel status,
            @Param("mes") int mes,
            @Param("ano") int ano
    );

    @Query("""
        SELECT COUNT(a) > 0 from Aluguel a
        where a.veiculo.id = :idVeiculo 
        and a.dataInicio <= :dataTermino 
        and a.dataTermino >= :dataInicio 
        and a.statusAluguel 
        not in :statusIgnorados
""")
    boolean existeSobreposicao(
            @Param("idVeiculo") int idVeiculo,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataTermino") LocalDate dataTermino,
            @Param("statusIgnorados") List<StatusAluguel> statusIgnorados
            );

    @Query("""
    SELECT COUNT(a) > 0 FROM Aluguel a
    WHERE a.veiculo.id = :idVeiculo
      AND a.id <> :idAluguel
      AND a.statusAluguel NOT IN :statusIgnorados
      AND a.dataInicio <= :dataTermino
      AND a.dataTermino >= :dataInicio
""")
    boolean existeSobreposicaoExcluindo(
            @Param("idVeiculo") int idVeiculo,
            @Param("idAluguel") int idAluguel,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataTermino") LocalDate dataTermino,
            @Param("statusIgnorados") List<StatusAluguel> statusIgnorados);



}
