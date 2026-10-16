package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
