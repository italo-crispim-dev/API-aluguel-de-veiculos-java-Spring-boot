package com.rental_car.rental_car.service;

import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FuncionarioDetailsService implements UserDetailsService {
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        Funcionario funcionario = funcionarioRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("Funcionario não encontrado"));

        return User.withUsername(funcionario.getEmail())
                .password(funcionario.getSenha())
                .authorities("ROLE_FUNCIONARIO")
                .build();
    }
}
