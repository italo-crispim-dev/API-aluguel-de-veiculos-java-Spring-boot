package com.rental_car.rental_car.service;

import com.rental_car.rental_car.exception.UserNaoEncontradoException;
import com.rental_car.rental_car.model.User;
import com.rental_car.rental_car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User add(User user){
        return userRepository.save(user);
    }

    public User findById(int id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNaoEncontradoException(
                        "Usuario de ID " + id + " Não foi encontrado"));
    }

    public List<User> findAll(){
        List<User> result = userRepository.findAll();
        return result;
    }

    public void delete(int id){
        findById(id);
        userRepository.deleteById(id);
    }

    public User update(int id, User novosdados){
        User user = findById(id);
        user.setNome(novosdados.getNome());
        return userRepository.save(user);
    }
}
