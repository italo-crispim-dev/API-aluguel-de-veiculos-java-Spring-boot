package com.rental_car.rental_car.service;

import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.model.StatusAluguel;
import com.rental_car.rental_car.model.StatusVeiculo;
import com.rental_car.rental_car.model.User;
import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.AluguelRepository;
import com.rental_car.rental_car.repository.UserRepository;
import com.rental_car.rental_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.util.List;


@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UserRepository userRepository;

    public Aluguel add(Aluguel aluguel){

        User user = userRepository.findById(aluguel.getCliente().getId()).get();
        Veiculo veiculo = veiculoRepository.findById(aluguel.getVeiculo().getId()).get();

        Long dias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataTermino());

        double valorTotal = dias * veiculo.getValor_diaria();

        aluguel.setCliente(user);
        veiculo.setStatusVeiculo(com.rental_car.rental_car.model.StatusVeiculo.ALUGADO);
        aluguel.setVeiculo(veiculo);
        aluguel.setValorAluguel(valorTotal);
        //Criar verificacao para saber se o aluguel é ativo ou agendado
        aluguel.setStatusAluguel(StatusAluguel.ATIVO);

        return aluguelRepository.save(aluguel);
    }

    public Aluguel delete(int id){

        Aluguel aluguel = aluguelRepository.findById(id).get();

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO) || aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO )){
            return null;
        }
        aluguel.setStatusAluguel(StatusAluguel.FINALIZADO);
        return aluguel;
    }

    public Aluguel update(int id, Aluguel novosDados){
        Aluguel aluguel = aluguelRepository.findById(id).get();
        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO) || aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO )){
            return null;
        }

        aluguel.setDataInicio(novosDados.getDataInicio());
        aluguel.setDataTermino(novosDados.getDataTermino());
        return aluguelRepository.save(aluguel);
    }

    public Aluguel findById(int id){
        return aluguelRepository.findById(id).get();
    }

    public List<Aluguel> findAll(){
        List<Aluguel> result = aluguelRepository.findAll();
        return result;
    }

}
