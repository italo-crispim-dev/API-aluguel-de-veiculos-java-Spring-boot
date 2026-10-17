package com.rental_car.rental_car.service;

import com.rental_car.rental_car.model.StatusVeiculo;
import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;


    public Veiculo add(Veiculo veiculo){
        veiculo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
        Veiculo result = veiculoRepository.save(veiculo);
        return result;
    }

    public List<Veiculo> allFind(){
        List<Veiculo> result = veiculoRepository.findAll();
        return result;
    }

    public Veiculo findById(int id){
        Veiculo result = veiculoRepository.findById(id).get();
        return result;
    }

    public void deleteById(int id){
        veiculoRepository.deleteById(id);
    }

    public Veiculo update(int id, Veiculo novosDados){
        Veiculo veiculo = veiculoRepository.findById(id).get();

        veiculo.setModelo(novosDados.getModelo());
        veiculo.setValor_diaria(novosDados.getValor_diaria());
        return veiculoRepository.save(veiculo);
    }

}
