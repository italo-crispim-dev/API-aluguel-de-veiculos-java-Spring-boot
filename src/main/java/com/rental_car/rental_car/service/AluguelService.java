package com.rental_car.rental_car.service;

import com.rental_car.rental_car.dto.AluguelResponseDto;
import com.rental_car.rental_car.dto.AluguelResquestDto;
import com.rental_car.rental_car.dto.AluguelUpdateDto;
import com.rental_car.rental_car.exception.*;
import com.rental_car.rental_car.model.*;

import com.rental_car.rental_car.repository.AluguelRepository;
import com.rental_car.rental_car.repository.AluguelRepository.MelhorFuncionarioProjection;
import lombok.RequiredArgsConstructor;
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
    private final ClienteService clienteService;
    private final FuncionarioService funcionarioService;

    //Comando de inserção equivalente ao SQL
    /* INSERT INTO tb_aluguel
        (id_cliente, id_funcionario, id_veiculo, data_inicio, data_termino, valor_aluguel, status_aluguel)
        VALUES (1,2,3, '2026-11-20', '2026-11-23', 600.00, 'AGENDADO');
    */
    @Transactional
    public AluguelResponseDto add(AluguelResquestDto dto, String emailFuncionario){

        //Equivalente em SQL
        // SELECT * from tb_cliente where id = 1;
        Cliente cliente = clienteService.findById(dto.getIdCliente());

        // SELECT * from tb_funcionario where id = 2;
        Funcionario funcionario = funcionarioService.findByEmail(emailFuncionario);

        // SELECT * from tb_veiculo where id = 3;
        Veiculo veiculo = veiculoService.buscarEntidade(dto.getIdVeiculo());

        if(veiculo.getStatusVeiculo() == StatusVeiculo.MANUNTENCAO){
            throw new VeiculoIndisponivelException("O veiculo de ID " + veiculo.getId() + " está em Manutenção no momento");
        }
        if(dto.getDataInicio().isAfter(dto.getDataTermino())){
            throw new DataInvalidaException("A data de término deve ser depois da data de início");
        }
        if(dto.getDataInicio().isBefore(LocalDate.now())){
            throw new DataInvalidaException("A data de inicio não pode está no passado");
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

        Long dias = Math.max(1L, ChronoUnit.DAYS.between(dto.getDataInicio(), dto.getDataTermino()));

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

            //Equivalente em SQL
            // UPDATE tb_veiculos SET status_veiculo = 'ALUGADO' where id = 3;
            veiculo.setStatusVeiculo(StatusVeiculo.ALUGADO);
        }

        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    @Transactional
    public AluguelResponseDto finaliza(int id){

        Aluguel aluguel = buscarEntidade(id);

        if(aluguel.getStatusAluguel() != StatusAluguel.ATIVO){
            throw new AluguelNaoPodeSerFinalizadoException("Só é possivel finalizar um aluguel com status ATIVO");
        }

        aluguel.setStatusAluguel(StatusAluguel.FINALIZADO);
        aluguel.getVeiculo().setStatusVeiculo(StatusVeiculo.DISPONIVEL);
        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
    }

    @Transactional
    public AluguelResponseDto update(int id, AluguelUpdateDto novosDados){
        /*
        UPDATE tb_aluguel
        SET id_veiculo = 9,
            id_funcionario = 1,
            id_cliente = 3,
            valor_aluguel = 1500.00,
            data_inicio = '2026-12-10',
            data_termino = '2026-12-20',
            status_aluguel = 'FINALIZADO'
        WHERE id = ?;
        */

        Aluguel aluguel = buscarEntidade(id);
        //SELECT * FROM tb_aluguel WHERE id = ?;

        if (aluguel.getStatusAluguel().equals(StatusAluguel.FINALIZADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que já terminou");
        }
        //SELECT * FROM tb_aluguel WHERE id = ? AND status_aluguel = 'FINALIZADO';

        if (aluguel.getStatusAluguel().equals(StatusAluguel.CANCELADO)){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel modificar um aluguel que foi cancelado");
        }
        //SELECT * FROM tb_aluguel WHERE id = ? AND status_aluguel = 'CANCELADO';

        if(novosDados.getDataInicio().isAfter(novosDados.getDataTermino())){
            throw new DataInvalidaException("A data de término deve ser depois da data de início");
        }
        // Impede que a data de início seja posterior à data de término.

        if (aluguel.getStatusAluguel() == StatusAluguel.AGENDADO){
            if(novosDados.getDataInicio().isBefore(LocalDate.now())){
                throw new DataInvalidaException("A nova data de inicio não pode estar no passado");
            }
            
            aluguel.setDataInicio(novosDados.getDataInicio());
            // Se o aluguel estiver agendado, permite alterar a data de início,
            // desde que ela não seja anterior à data atual.
        }
        else {
            if(!novosDados.getDataInicio().equals(aluguel.getDataInicio())){
                throw new AluguelNaoPodeSerAlteradoException("Não é possivel alterar a data de inicio de um aluguel ativo");
            }
            if (novosDados.getDataTermino().isBefore(LocalDate.now())) {
                throw new DataInvalidaException("A data de término de um aluguel ativo não pode estar no passado");
            }

            // Se o aluguel estiver ativo, impede a alteração da data de início
            // e permite alterar a data de término somente para uma data futura ou atual
        }

        aluguel.setDataTermino(novosDados.getDataTermino());
        // Atualiza a data de término do aluguel após as validações anteriores

        boolean ocupado = aluguelRepository.existeSobreposicaoExcluindo(
                aluguel.getVeiculo().getId(),
                aluguel.getId(),
                aluguel.getDataInicio(),
                aluguel.getDataTermino(),
                List.of(StatusAluguel.FINALIZADO, StatusAluguel.CANCELADO)
        );
        /*
           SELECT COUNT(*) > 0
           FROM tb_aluguel
           WHERE id_veiculo = ?                                    -- mesmo veículo
              AND id <> ?                                           -- ignora o próprio aluguel
              AND status_aluguel NOT IN ('FINALIZADO', 'CANCELADO')
              AND data_inicio  <= ?                                 -- começa antes do meu término
              AND data_termino >= ?;                                -- termina depois do meu início
          Retorna true se existe conflito de datas para esse veículo.
         */


        if(ocupado){
            throw new VeiculoIndisponivelException("Veiculo já alugado nesse periodo");
        }

        Long dias = Math.max(1L, ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataTermino()));
        // Calcula os dias entre o início e o término e pega o maior entre 1 e esse valor,
        // garantindo no mínimo 1 diária. O resultado fica guardado em "dias".

        BigDecimal valor = aluguel.getVeiculo().getValorDiaria();
        // Busca o valor da diária do veículo do aluguel
        // SELECT v.valor_diaria FROM tb_aluguel a JOIN tb_veiculo v ON v.id = a.id_veiculo WHERE a.id = ?;

        aluguel.setValorAluguel(valor.multiply(BigDecimal.valueOf(dias)));

        return AluguelResponseDto.fromEntity(aluguelRepository.save(aluguel));
        /*
        UPDATE tb_aluguel
        SET data_inicio = ?, data_termino = ?, valor_aluguel = ?
            WHERE id = ?;

         */
    }

    //Equivalente em SQL
    // SELECT * FROM tb_aluguel WHERE id = 1;
    public AluguelResponseDto findById(int id){
       Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() ->
                        new AluguelNaoEncontradoException(
                                "Alguel de ID " + id + " não foi encontrado"));

       return AluguelResponseDto.fromEntity(aluguel);
    }

    //Equivalente em SQL
    // SELECT * FROM tb_aluguel;
    public List<AluguelResponseDto> findAll(){
        return aluguelRepository.findAll().stream().map(AluguelResponseDto::fromEntity).toList();
    }

    @Transactional
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
        Cliente cliente = clienteService.findById(idCliente);
        return aluguelRepository.findByClienteId(idCliente).stream().map(AluguelResponseDto::fromEntity).toList();
   }

    private Aluguel buscarEntidade(int id){
        return aluguelRepository.findById(id)
                .orElseThrow(() ->
                        new AluguelNaoEncontradoException("Aluguel de ID "+ id + " não encontrado"));
    }

    @Transactional
    public void delete(int id){
        Aluguel aluguel = buscarEntidade(id);
        // SELECT * FROM tb_aluguel WHERE id = ?;
        // Carrega o aluguel para validar o status antes de excluir.

        if(aluguel.getStatusAluguel() == StatusAluguel.ATIVO){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel excluir um aluguel ATIVO");
        }

        if(aluguel.getStatusAluguel() == StatusAluguel.FINALIZADO){
            throw new AluguelNaoPodeSerAlteradoException("Não é possivel excluir um aluguel FINALIZADO");
        }
        // Em SQL, a regra equivaleria a: DELETE FROM tb_aluguel WHERE id = ? AND status_aluguel IN ('AGENDADO','CANCELADO')

        aluguelRepository.delete(aluguel);
        // DELETE FROM tb_aluguel WHERE id = ?;
    }

    //consultas
    public List<MelhorFuncionarioProjection> buscarPorMelhorFuncionario(){
        return aluguelRepository.buscarPorMelhorFuncionario();
    }


}
