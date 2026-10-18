package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.PlacaJaCadastradaException;
import com.rental_car.rental_car.exception.VeiculoNaoEncontradoException;
import com.rental_car.rental_car.exception.VeiculoNaoPodeSerApagadoException;
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
        if(veiculoRepository.existByPlaca(veiculo.getPlaca())){
            throw new PlacaJaCadastradaException("A placa + " + veiculo.getPlaca() + " já foi cadastrada");
        }
        veiculo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
        Veiculo result = veiculoRepository.save(veiculo);
        return result;
    }

    public List<Veiculo> allFind(){
        List<Veiculo> result = veiculoRepository.findAll();
        return result;
    }

    public Veiculo findById(int id){

        return veiculoRepository.findById(id)
                        .orElseThrow(() -> new VeiculoNaoEncontradoException(
                                "Veículo de ID " + id + " não foi encontrado."));

    }

    public void deleteById(int id){
        Veiculo veiculo = findById(id);

        if (veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO ){
            throw new VeiculoNaoPodeSerApagadoException("Veiculo de ID " + id + " está alugado e não pode ser apagado");
        }
        if (veiculo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
            throw new VeiculoNaoPodeSerApagadoException("Veiculo de ID " + id + " está em manuntenção e não pode ser apagado");
        }

        veiculoRepository.deleteById(id);
    }

    public Veiculo update(int id, Veiculo novosDados){
        Veiculo veiculo = findById(id);

        if (veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO ){
            throw new VeiculoNaoPodeSerApagadoException("Veiculo de ID " + id + " está alugado e não pode ser modificado");
        }

        veiculo.setModelo(novosDados.getModelo());
        veiculo.setValor_diaria(novosDados.getValor_diaria());
        return veiculoRepository.save(veiculo);
    }



}
