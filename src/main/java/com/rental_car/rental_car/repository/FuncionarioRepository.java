package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.model.StatusFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Query("select f from Funcionario f where f.email = :email and f.statusFuncionario = :status")
    Optional<Funcionario> findByEmail(@Param("email") String email, @Param("status") StatusFuncionario status);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, int id);

    @Query("SELECT f from Funcionario f where f.statusFuncionario = :status")
    List<Funcionario> buscaPorAtivos(@Param("status") StatusFuncionario status);

    @Query("SELECT f from Funcionario f where f.statusFuncionario = :status and f.id = :id")
    Optional<Funcionario> buscaPorIdStatus(@Param("id") int id, @Param("status")StatusFuncionario status);
}
