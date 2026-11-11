package com.rental_car.rental_car.repository;

import com.rental_car.rental_car.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {

    boolean existsByPlaca(String placa);

    boolean existsByPlacaAndIdNot(String placa, int id);

    //consultas
    public interface MarcaAlugadaProjection{
        String getMarca();
        Long getTotalAlugueis();
    }
    @Query(value = """
    SELECT 
        v.marca AS marca,
        COUNT(a.id) AS totalAlugueis
    FROM tb_veiculo v
    INNER JOIN tb_aluguel a
        ON v.id = a.id_veiculo
    GROUP BY v.marca
    ORDER BY totalAlugueis DESC
    """, nativeQuery = true)
    public List<MarcaAlugadaProjection> buscarMarcaMaisAlugada();

}
