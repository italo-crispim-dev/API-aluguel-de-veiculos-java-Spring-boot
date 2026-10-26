package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.EstadoInvalidoException;
import com.rental_car.rental_car.exception.ManutencaoNaoEncontradaException;
import com.rental_car.rental_car.exception.VeiculoNaoEncontradoException;
import com.rental_car.rental_car.model.Manutencao;
import com.rental_car.rental_car.model.StatusManutencao;
import com.rental_car.rental_car.model.StatusVeiculo;
import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.ManutencaoRepository;
import com.rental_car.rental_car.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ManutencaoService {
    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Manutencao add(Manutencao manutencao){
        int idVeiculo = manutencao.getVeiculo().getId();

        Veiculo veiculo = veiculoRepository.findById(idVeiculo).
                orElseThrow(() -> new VeiculoNaoEncontradoException("Veiculo de ID" + idVeiculo + " não foi encontrado."));


        if(veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO){
            throw new EstadoInvalidoException("Não é possivel iniciar manutencao em um veiculo alugado");
        }

        manutencao.setVeiculo(veiculo);
        manutencao.setStatusManutencao(StatusManutencao.ATIVA);

        veiculo.setStatusVeiculo(StatusVeiculo.MANUNTENCAO);
        veiculoRepository.save(veiculo);

        return manutencaoRepository.save(manutencao);
    }

    public List<Manutencao> listarTodos(){
        return manutencaoRepository.findAll();
    }

    public Manutencao buscarPorId(int idBusca){
        return manutencaoRepository.findById(idBusca).
                orElseThrow(() -> new ManutencaoNaoEncontradaException("Manutencao de ID: " +idBusca+ " não encontrada."));
    }

    @Transactional
    public Manutencao atualizar(int id, Manutencao dadosAtualizados){
        Manutencao manutencao = buscarPorId(id);

        if(manutencao.getStatusManutencao() == StatusManutencao.FINALIZADA){
            throw new EstadoInvalidoException("Não é possível atualizar uma manutenção finalizada.");
        }

        Veiculo veiculoAntigo = manutencao.getVeiculo();
        int idVeiculoNovo = dadosAtualizados.getVeiculo().getId();

        if(veiculoAntigo.getId() != idVeiculoNovo){
            Veiculo veiculoNovo = veiculoRepository.findById(idVeiculoNovo).
                    orElseThrow(() -> new VeiculoNaoEncontradoException("Veiculo de ID: " +idVeiculoNovo+ " não foi encontrado"));

            if(veiculoNovo.getStatusVeiculo() == StatusVeiculo.ALUGADO || veiculoNovo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
                throw new EstadoInvalidoException("O veículo escolhido está alugado ou já está em manutenção.");
            }

            boolean outraManutencaoAtiva = manutencaoRepository.existsByVeiculoIdAndStatusManutencaoAndIdNot(veiculoAntigo.getId(), StatusManutencao.ATIVA, id);

            if(!outraManutencaoAtiva){
                veiculoAntigo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
                veiculoRepository.save(veiculoAntigo);
            }

            veiculoNovo.setStatusVeiculo(StatusVeiculo.MANUNTENCAO);
            veiculoRepository.save(veiculoNovo);
            manutencao.setVeiculo(veiculoNovo);
        }

        manutencao.setValor(dadosAtualizados.getValor());
        manutencao.setDataManutencao(dadosAtualizados.getDataManutencao());
        manutencao.setDescricao(dadosAtualizados.getDescricao());

        return manutencaoRepository.save(manutencao);
    }

    @Transactional
    public Manutencao finalizar(int idManutencao){
        Manutencao manutencao = buscarPorId(idManutencao);
        Veiculo veiculo = manutencao.getVeiculo();

        if(manutencao.getStatusManutencao() == StatusManutencao.FINALIZADA){
            throw new EstadoInvalidoException("Está manutenção ja foi finalizada");
        }

        boolean outraManutencaoAtiva = manutencaoRepository.existsByVeiculoIdAndStatusManutencaoAndIdNot(veiculo.getId(), StatusManutencao.ATIVA, idManutencao);

        if(!outraManutencaoAtiva){
            veiculo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
            veiculoRepository.save(veiculo);
        }
        manutencao.setStatusManutencao(StatusManutencao.FINALIZADA);

        return manutencaoRepository.save(manutencao);
    }

    @Transactional
    public void deletar(int idManutencao){
        Manutencao manutencao = buscarPorId(idManutencao);

        if(manutencao.getStatusManutencao() == StatusManutencao.FINALIZADA){
            throw new EstadoInvalidoException("Não é possivel deletar uma manutenção finalizada.");
        }

        Veiculo veiculo = manutencao.getVeiculo();

        boolean outraManutencaoAtiva = manutencaoRepository.existsByVeiculoIdAndStatusManutencaoAndIdNot(veiculo.getId(), StatusManutencao.ATIVA, idManutencao);

        if(!outraManutencaoAtiva){
            veiculo.setStatusVeiculo(StatusVeiculo.DISPONIVEL);
            veiculoRepository.save(veiculo);
        }

        manutencaoRepository.delete(manutencao);
    }
}