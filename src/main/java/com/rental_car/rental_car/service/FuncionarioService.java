package com.rental_car.rental_car.service;

import com.rental_car.rental_car.dto.FuncionarioRequestDto;
import com.rental_car.rental_car.dto.FuncionarioResponseDto;
import com.rental_car.rental_car.exception.EmailJaCadastradoException;
import com.rental_car.rental_car.exception.FuncionarioNaoEncontradoException;
import com.rental_car.rental_car.exception.FuncionarioNaoPodeSerModificadoException;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.model.StatusFuncionario;
import com.rental_car.rental_car.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public FuncionarioResponseDto add(FuncionarioRequestDto funcionarioDto){

        if(funcionarioRepository.existsByEmail(funcionarioDto.getEmail())){
            throw new EmailJaCadastradoException("O email informado já existe no sistema");
        }
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setSalario(funcionarioDto.getSalario());
        funcionario.setEmail(funcionarioDto.getEmail());
        funcionario.setStatusFuncionario(StatusFuncionario.ATIVO);
        String senhaCriptografada = passwordEncoder.encode(funcionarioDto.getSenha());
        funcionario.setSenha(senhaCriptografada);
        return FuncionarioResponseDto.fromEntity(funcionarioRepository.save(funcionario));
    }

    public FuncionarioResponseDto findById(int id){
        return FuncionarioResponseDto.fromEntity(funcionarioRepository.buscaPorIdStatus(id, StatusFuncionario.ATIVO).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario de ID " + id + " Não foi encontrado")));
    }

    public List<FuncionarioResponseDto> findAll(){
        return funcionarioRepository.buscaPorAtivos(StatusFuncionario.ATIVO)
                .stream()
                .map(FuncionarioResponseDto::fromEntity)
                .toList();
    }
    @Transactional
    public void inativarFuncionario(int id){
        Funcionario funcionario = buscaFuncionarioPorId(id);
        if (funcionario.getStatusFuncionario() == StatusFuncionario.INATIVO){
            throw new FuncionarioNaoPodeSerModificadoException("Só é possivel inativar um funcionario ATIVO");
        }
        funcionario.setStatusFuncionario(StatusFuncionario.INATIVO);
    }

    @Transactional
    public void ativarFuncionario(int id){
        Funcionario funcionario = buscaQualquerStatus(id);
        if (funcionario.getStatusFuncionario() == StatusFuncionario.ATIVO){
            throw new FuncionarioNaoPodeSerModificadoException("Só é possivel ativar um funcionario INATIVO");
        }
        funcionario.setStatusFuncionario(StatusFuncionario.ATIVO);
    }

    @Transactional
    public FuncionarioResponseDto update(int id, FuncionarioRequestDto novosDados){
        Funcionario funcionario = buscaFuncionarioPorId(id);
        if(funcionarioRepository.existsByEmailAndIdNot(novosDados.getEmail(), id)){
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
        funcionario.setNome(novosDados.getNome());
        funcionario.setSalario(novosDados.getSalario());
        funcionario.setEmail(novosDados.getEmail());
        String senhaCriptografada = passwordEncoder.encode(novosDados.getSenha());
        funcionario.setSenha(senhaCriptografada);

        return FuncionarioResponseDto.fromEntity(funcionarioRepository.save(funcionario));
    }

    public Funcionario findByEmail(String email){
        return funcionarioRepository.findByEmail(email, StatusFuncionario.ATIVO).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario com email " + email + " não foi encontrado"));
    }

    public Funcionario buscaFuncionarioPorId(int id){
        return funcionarioRepository.buscaPorIdStatus(id, StatusFuncionario.ATIVO).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario de ID " + id + " não foi encontrado"));
    }

    private Funcionario buscaQualquerStatus(int id) {
        return funcionarioRepository.findById(id)
                .orElseThrow(() -> new FuncionarioNaoEncontradoException("Funcionario de ID " + id + " não foi encontrado"));
    }

}
