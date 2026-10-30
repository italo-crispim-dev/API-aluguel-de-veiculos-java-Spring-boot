package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.LoginRequestDto;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")

public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> add(@RequestBody Funcionario funcionario){
        Funcionario result = funcionarioService.add(funcionario);
        return ResponseEntity.status(201).body(result);

    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> findAll(){
        List<Funcionario> result = funcionarioService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Funcionario> findById(@PathVariable int id){
        Funcionario result = funcionarioService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Funcionario> update(@PathVariable int id, @RequestBody Funcionario novosDados){
        Funcionario funcionario = funcionarioService.update(id, novosDados);
        return ResponseEntity.ok().body(funcionario);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        funcionarioService.deleteByID(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Funcionario> login(@RequestBody LoginRequestDto loginRequest){
        Funcionario funcionario = funcionarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
        return ResponseEntity.ok(funcionario);
    }


}
