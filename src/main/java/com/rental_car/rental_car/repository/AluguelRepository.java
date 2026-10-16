package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {
}
