package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Cliente, Integer> {
}
