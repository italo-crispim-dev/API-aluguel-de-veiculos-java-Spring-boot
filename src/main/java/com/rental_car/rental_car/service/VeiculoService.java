package com.rental_car.rental_car.service;

import com.rental_car.rental_car.dto.VeiculoRequestDTO;
import com.rental_car.rental_car.dto.VeiculoResponseDTO;
import com.rental_car.rental_car.exception.PlacaJaCadastradaException;
import com.rental_car.rental_car.exception.VeiculoNaoEncontradoException;
import com.rental_car.rental_car.exception.VeiculoNaoPodeSerApagadoException;
import com.rental_car.rental_car.exception.VeiculoNaoPodeSerModificadoException;
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


    public VeiculoResponseDTO add(VeiculoRequestDTO dto){
        if(veiculoRepository.existsByPlaca(dto.getPlaca().toUpperCase())){
            throw new PlacaJaCadastradaException("A placa " + dto.getPlaca() + " já foi cadastrada");
        }
        Veiculo result = veiculoRepository.save(dto.toEntity());
        return VeiculoResponseDTO.fromEntity(result);
    }

    public List<VeiculoResponseDTO> findAll(){
        return veiculoRepository.findAll().stream().map(VeiculoResponseDTO::fromEntity).toList();
    }

    public VeiculoResponseDTO findById(int id){
        return VeiculoResponseDTO.fromEntity(buscarEntidade(id));
    }

    public Veiculo buscarEntidade(int id){
        return veiculoRepository.findById(id).orElseThrow(() -> new VeiculoNaoEncontradoException(
                "Veiculo de ID " + id + " não foi encontrado"
        ));
    }

    public void deleteById(int id){
        Veiculo veiculo = buscarEntidade(id);

        if (veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO ){
            throw new VeiculoNaoPodeSerApagadoException("Veiculo de ID " + id + " está alugado e não pode ser apagado");
        }
        if (veiculo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
            throw new VeiculoNaoPodeSerApagadoException("Veiculo de ID " + id + " está em manuntenção e não pode ser apagado");
        }

        veiculoRepository.delete(veiculo);
    }

    public VeiculoResponseDTO update(int id, VeiculoRequestDTO novosDados){
        Veiculo veiculo = buscarEntidade(id);

        if (veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO ){
            throw new VeiculoNaoPodeSerModificadoException("Veiculo de ID " + id + " está alugado e não pode ser modificado");
        }
        String placa = novosDados.getPlaca().toUpperCase();
        if(veiculoRepository.existsByPlacaAndIdNot(placa, id)){
            throw new PlacaJaCadastradaException("A placa " + placa + " já foi cadastrada");
        }

        veiculo.setModelo(novosDados.getModelo());
        veiculo.setMarca(novosDados.getMarca());
        veiculo.setPlaca(placa);
        veiculo.setValorDiaria(novosDados.getValorDiaria());
        return VeiculoResponseDTO.fromEntity(veiculoRepository.save(veiculo));
    }

}
