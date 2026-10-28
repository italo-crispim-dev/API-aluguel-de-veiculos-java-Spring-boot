package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Manutencao;
import com.rental_car.rental_car.model.StatusManutencao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Integer> {
    boolean existsByVeiculoIdAndStatusManutencaoAndIdNot(int veiculoId, StatusManutencao statusManutencao, int id);
}
