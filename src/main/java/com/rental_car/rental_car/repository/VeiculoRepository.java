package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
}
