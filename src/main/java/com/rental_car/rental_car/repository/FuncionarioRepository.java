package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}
