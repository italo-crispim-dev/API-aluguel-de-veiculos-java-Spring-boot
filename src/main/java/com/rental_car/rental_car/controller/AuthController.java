package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.LoginRequestDto;
import com.rental_car.rental_car.dto.LoginResponseDto;
import com.rental_car.rental_car.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request){
        System.out.println("ENTROU NO AUTH CONTROLLER");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getSenha()
                    )
            );

            System.out.println("AUTENTICAÇÃO REALIZADA COM SUCESSO!");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        String token = jwtService.gerarToken(request.getEmail());

        return new LoginResponseDto(token);
    }

}
