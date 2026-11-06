package com.rental_car.rental_car.service;

import com.rental_car.rental_car.dto.AluguelResponseDto;
import com.rental_car.rental_car.dto.AluguelResquestDto;
import com.rental_car.rental_car.dto.AluguelUpdateDto;
import com.rental_car.rental_car.exception.*;
import com.rental_car.rental_car.model.*;

import com.rental_car.rental_car.repository.AluguelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AluguelService {


    private final AluguelRepository aluguelRepository;
    private final VeiculoService veiculoService;
    private final UserService userService;
    private final FuncionarioService funcionarioService;

    @Transactional
    public AluguelResponseDto add(AluguelResquestDto dto){

        Cliente cliente = userService.findById(dto.getIdCliente());

        Veiculo veiculo = veiculoService.buscarEntidade(dto.getIdVeiculo());

        Funcionario funcionario = funcionarioService.findById(dto.getIdFuncionario());

        if(veiculo.getStatusVeiculo() == StatusVeiculo.ALUGADO){
            throw new VeiculoIndisponivelException("O veiculo de ID " + veiculo.getId() + " está alugado no momento");
        }

        if(veiculo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
            throw new VeiculoIndisponivelException("O veiculo de ID " + veiculo.getId() + " está em Manutenção no momento");
        }
        if(dto.getDataInicio().isAfter(dto.getDataTermino())){
            throw new DataInvalidaException("A data de término deve ser depois da data de início");
        }
        boolean ocupado = aluguelRepository.existeSobreposicao(
                dto.getIdVeiculo(),
                dto.getDataInicio(),
                dto.getDataTermino(),
                List.of(StatusAluguel.FINALIZADO, StatusAluguel.CANCELADO)
        );
        if(ocupado){
            throw new VeiculoIndisponivelException("Veiculo já alugado nesse periodo");
        }

        Long dias = ChronoUnit.DAYS.between(dto.getDataInicio(), dto.getDataTermino());

        Aluguel aluguel = new Aluguel();
        aluguel.setVeiculo(veiculo);
        aluguel.setCliente(cliente);
        aluguel.setFuncionario(funcionario);
        aluguel.setDataInicio(dto.getDataInicio());
        aluguel.setDataTermino(dto.getDataTermino());
        aluguel.setValorAluguel(veiculo.getValorDiaria().multiply(BigDecimal.valueOf(dias)));

        if(dto.getDataInicio().isAfter(LocalDate.now())){
            aluguel.setStatusAluguel(StatusAluguel.AGENDADO);
        }
        else{
            aluguel.setStatusAluguel(StatusAluguel.ATIVO);
            veiculo.setStatusVeiculo(StatusVeiculo.ALUGADO);
        }

        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    @Transactional
    public AluguelResponseDto finaliza(int id){

        Aluguel aluguel = buscarEntidade(id);

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO)){
            throw new AluguelNaoPodeSerFinalizadoException("Não é possivel finalizar um aluguel que já terminou");
        }
        if (aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO)){
            throw new AluguelNaoPodeSerFinalizadoException("Não é possivel finalizar um aluguel que foi cancelado");
        }

        aluguel.setStatusAluguel(StatusAluguel.FINALIZADO);
        aluguel.getVeiculo().setStatusVeiculo(StatusVeiculo.DISPONIVEL);
        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    @Transactional
    public AluguelResponseDto update(int id, AluguelUpdateDto novosDados){
        Aluguel aluguel = buscarEntidade(id);

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que já terminou");
        }
        if (aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que foi cancelado");
        }
        if(novosDados.getDataInicio().isAfter(novosDados.getDataTermino())){
            throw new DataInvalidaException("A data de término deve ser depois da data de início");
        }

        if (aluguel.getStatusAluguel() == StatusAluguel.AGENDADO){
            if(novosDados.getDataInicio().isBefore(LocalDate.now())){
                throw new DataInvalidaException("A nova data de inicio não pode estar no passado");
            }
            
            aluguel.setDataInicio(novosDados.getDataInicio());
        }
        else {
            if(!novosDados.getDataInicio().equals(aluguel.getDataInicio())){
                throw new AluguelNaoPodeSerAlteradoException("Não é possivel alterar a data de inicio de um aluguel ativo");
            }
            if (novosDados.getDataTermino().isBefore(LocalDate.now())) {
                throw new DataInvalidaException("A data de término de um aluguel ativo não pode estar no passado");
            }
        }
        aluguel.setDataTermino(novosDados.getDataTermino());

        Veiculo veiculo = veiculoService.buscarEntidade(aluguel.getVeiculo().getId());
        Long dias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataTermino());
        BigDecimal valor = veiculo.getValorDiaria();
        aluguel.setValorAluguel(valor.multiply(BigDecimal.valueOf(dias)));

        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    public AluguelResponseDto findById(int id){
       Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() ->
                        new AluguelNaoEncontradoException(
                                "Alguel de ID " + id + " não foi encontrado"));

       return AluguelResponseDto.fromEntity(aluguel);
    }


    public List<AluguelResponseDto> findAll(){
        return aluguelRepository.findAll().stream().map(AluguelResponseDto::fromEntity).toList();
    }

    public AluguelResponseDto cancelarAluguel(int id){
        Aluguel aluguel = buscarEntidade(id);

        if (aluguel.getStatusAluguel() == StatusAluguel.FINALIZADO){
             throw new AluguelNaoPodeSerAlteradoException("Não é possivel cancelar um aluguel FINALIZADO ");
        }
        if (aluguel.getStatusAluguel() == StatusAluguel.CANCELADO){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel cancelar um aluguel já CANCELADO");
        }
        if (aluguel.getStatusAluguel() == StatusAluguel.ATIVO){
            throw new AluguelNaoPodeSerAlteradoException("Alugueis ativos só podem ser FINALIZADOS");
        }

        aluguel.setStatusAluguel(StatusAluguel.CANCELADO);
        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    public BigDecimal valorAluguelPorMes(int mes, int ano){
        if(mes < 1 || mes > 12){
            throw new ParametroInvalidoException("Mês deve estar entre 1 e 12");
        }
        int anoAtual = Year.now().getValue();
        if (ano < 2025 || ano > anoAtual){
            throw new ParametroInvalidoException("O ano deve estar entre 2025 e " + anoAtual);
        }
        return aluguelRepository.valorAluguelPorMes(StatusAluguel.FINALIZADO, mes, ano);
    }

    public List<AluguelResponseDto> findByCliente(int idCliente){
        Cliente cliente = userService.findById(idCliente);
        return aluguelRepository.findByClienteId(idCliente).stream().map(AluguelResponseDto::fromEntity).toList();
   }

    private Aluguel buscarEntidade(int id){
        return aluguelRepository.findById(id)
                .orElseThrow(() ->
                        new AluguelNaoEncontradoException("Aluguel de ID "+ id + " não encontrado"));
    }


}
