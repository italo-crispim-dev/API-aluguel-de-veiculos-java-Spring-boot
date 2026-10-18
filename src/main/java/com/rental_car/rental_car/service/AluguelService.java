package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.*;
import com.rental_car.rental_car.model.*;

import com.rental_car.rental_car.repository.AluguelRepository;
import com.rental_car.rental_car.repository.UserRepository;
import com.rental_car.rental_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private UserService userService;

    public Aluguel add(Aluguel aluguel){

        User user = userService.findById(aluguel.getCliente().getId());
        Veiculo veiculo = veiculoService.findById(aluguel.getVeiculo().getId());

        if(veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO){
            throw new VeiculoIndisponivelException("O veiculo de ID " + veiculo.getId() + " está alugado no momento");
        }

        if(veiculo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
            throw new VeiculoIndisponivelException("O veiculo de ID " + veiculo.getId() + " está em Manutenção no momento");
        }

        Long dias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataTermino());

        double valorTotal = dias * veiculo.getValor_diaria();

        aluguel.setCliente(user);
        veiculo.setStatusVeiculo(StatusVeiculo.ALUGADO);
        aluguel.setVeiculo(veiculo);
        aluguel.setValorAluguel(valorTotal);

        LocalDate hoje = LocalDate.now();

        if(aluguel.getDataInicio().isEqual(hoje)){
            aluguel.setStatusAluguel(StatusAluguel.ATIVO);
        }
        else if (aluguel.getDataInicio().isAfter(hoje)){
            aluguel.setStatusAluguel(StatusAluguel.AGENDADO);
        }

        aluguel.setStatusAluguel(StatusAluguel.ATIVO);

        return aluguelRepository.save(aluguel);
    }

    public Aluguel finaliza(int id){

        Aluguel aluguel = findById(id);

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO)){
            throw new AluguelNaoPodeSerFinalizadoException("Não é possivel finalizar um aluguel que já terminou");
        }
        if (aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO)){
            throw new AluguelNaoPodeSerFinalizadoException("Não é possivel finalizar um aluguel que foi cancelado");
        }

        aluguel.setStatusAluguel(StatusAluguel.FINALIZADO);
        return aluguel;
    }

    public Aluguel update(int id, Aluguel novosDados){
        Aluguel aluguel = findById(id);

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que já terminou");
        }
        if (aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que foi cancelado");
        }

        aluguel.setDataInicio(novosDados.getDataInicio());
        aluguel.setDataTermino(novosDados.getDataTermino());
        return aluguelRepository.save(aluguel);
    }

    public Aluguel findById(int id){
        return aluguelRepository.findById(id)
                .orElseThrow(() -> new AluguelNaoEncontradoException("Alguel de ID " + id + " não foi encontrado"));
    }

    public List<Aluguel> findAll(){
        List<Aluguel> result = aluguelRepository.findAll();
        return result;
    }

}
