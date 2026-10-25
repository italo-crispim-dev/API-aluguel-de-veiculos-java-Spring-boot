package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.FuncionarioNaoEncontradoException;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario add(Funcionario funcionario){
        return funcionarioRepository.save(funcionario);
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

    public Funcionario update(int id, Funcionario novosDados){
        Funcionario funcionario = findById(id);
        funcionario.setNome(novosDados.getNome());
        funcionario.setSalario(novosDados.getSalario());
        return funcionarioRepository.save(funcionario);
    }
}
