package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Manutencao;
import com.rental_car.rental_car.model.StatusManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {
    boolean existsByVeiculoIdAndStatusManutencaoAndIdNot(int veiculoId, StatusManutencao statusManutencao, int id);


    public interface MarcaManutencaoProjection {

        String getMarca();

        Long getTotalManutencoes();
    }

    @Query(value = """
    SELECT
        v.marca AS marca,
        COUNT(m.id) AS totalManutencoes
    FROM tb_veiculo v
    INNER JOIN tb_manutencao m
        ON v.id = m.veiculo_id
    GROUP BY v.marca
    ORDER BY totalManutencoes DESC
    """, nativeQuery = true)
    List<MarcaManutencaoProjection> buscarMarcasComMaisManutencoes();

}