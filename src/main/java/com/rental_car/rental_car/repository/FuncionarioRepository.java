package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByEmail(String email);
}
