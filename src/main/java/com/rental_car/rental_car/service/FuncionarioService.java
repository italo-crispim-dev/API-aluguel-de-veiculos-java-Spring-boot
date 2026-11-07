package com.rental_car.rental_car.service;

import com.rental_car.rental_car.dto.FuncionarioRequestDto;
import com.rental_car.rental_car.dto.FuncionarioResponseDto;
import com.rental_car.rental_car.exception.EmailJaCadastradoException;
import com.rental_car.rental_car.exception.FuncionarioNaoEncontradoException;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.repository.FuncionarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder passwordEncoder){
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public FuncionarioResponseDto add(FuncionarioRequestDto funcionarioDto){

        if(funcionarioRepository.existsByEmail(funcionarioDto.getEmail())){
            throw new EmailJaCadastradoException("O email informado já existe no sistema");
        }
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(funcionarioDto.getNome());
        funcionario.setSalario(funcionarioDto.getSalario());
        funcionario.setEmail(funcionario.getEmail());

        String senhaCriptografada = passwordEncoder.encode(funcionarioDto.getSenha());
        funcionario.setSenha(senhaCriptografada);
        return FuncionarioResponseDto.fromEntity(funcionarioRepository.save(funcionario));
    }

    public Funcionario findById(int id){
        return funcionarioRepository.findById(id).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario de ID " + id + " Não foi encontrado"));
    }

    public List<Funcionario> findAll(){
        return funcionarioRepository.findAll();
    }

    public void deleteByID(int id){
        Funcionario funcionario = findById(id);
        funcionarioRepository.deleteById(id);
        return;
    }

    public FuncionarioResponseDto update(int id, FuncionarioRequestDto novosDados){
        Funcionario funcionario = findById(id);
        funcionario.setNome(novosDados.getNome());
        funcionario.setSalario(novosDados.getSalario());
        if(funcionarioRepository.existsByEmailAndIdNot(novosDados.getEmail(), id)){
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
        funcionario.setEmail(novosDados.getEmail());
        String senhaCriptografada = passwordEncoder.encode(novosDados.getSenha());
        funcionario.setSenha(senhaCriptografada);

        return FuncionarioResponseDto.fromEntity(funcionarioRepository.save(funcionario));
    }

    public Funcionario findByEmail(String email){
        return funcionarioRepository.findByEmail(email).orElseThrow(() ->
                new FuncionarioNaoEncontradoException("Funcionario com email " + email + " não foi encontrado"));
    }

}
