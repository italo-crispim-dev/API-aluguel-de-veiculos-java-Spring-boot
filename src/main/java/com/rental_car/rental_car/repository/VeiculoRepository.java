package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    boolean existsByPlaca(String placa);

    boolean existsByPlacaAndIdNot(String placa, int id);

}
