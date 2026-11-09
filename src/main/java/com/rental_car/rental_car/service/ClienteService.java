package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.UserNaoEncontradoException;
import com.rental_car.rental_car.model.Cliente;
import com.rental_car.rental_car.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente add(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente findById(int id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new UserNaoEncontradoException(
                        "Usuario de ID " + id + " Não foi encontrado"));
    }

    public List<Cliente> findAll(){
        List<Cliente> result = clienteRepository.findAll();
        return result;
    }

    public void delete(int id){
        findById(id);
        clienteRepository.deleteById(id);
    }

    public Cliente update(int id, Cliente novosdados){
        Cliente cliente = findById(id);
        cliente.setNome(novosdados.getNome());
        cliente.setEmail(novosdados.getEmail());
        return clienteRepository.save(cliente);
    }
}
