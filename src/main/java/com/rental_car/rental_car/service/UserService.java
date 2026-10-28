package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.UserNaoEncontradoException;
import com.rental_car.rental_car.model.Cliente;
import com.rental_car.rental_car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Cliente add(Cliente cliente){
        return userRepository.save(cliente);
    }

    public Cliente findById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNaoEncontradoException(
                        "Usuario de ID " + id + " Não foi encontrado"));
    }

    public List<Cliente> findAll(){
        List<Cliente> result = userRepository.findAll();
        return result;
    }

    public void delete(int id){
        findById(id);
        userRepository.deleteById(id);
    }

    public Cliente update(int id, Cliente novosdados){
        Cliente cliente = findById(id);
        cliente.setNome(novosdados.getNome());
        return userRepository.save(cliente);
    }
}
