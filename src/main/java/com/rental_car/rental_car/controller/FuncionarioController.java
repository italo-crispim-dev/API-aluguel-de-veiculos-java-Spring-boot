package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.FuncionarioRequestDto;
import com.rental_car.rental_car.dto.FuncionarioResponseDto;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> add(@Valid  @RequestBody FuncionarioRequestDto funcionario){
        FuncionarioResponseDto result = funcionarioService.add(funcionario);
        return ResponseEntity.status(201).body(result);

    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDto>> findAll(){
        List<FuncionarioResponseDto> response = funcionarioService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDto> findById(@PathVariable int id){
        FuncionarioResponseDto funcionario = funcionarioService.findById(id);
        return ResponseEntity.ok().body(funcionario);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDto> update(@PathVariable int id, @Valid @RequestBody FuncionarioRequestDto novosDados){
        FuncionarioResponseDto funcionario = funcionarioService.update(id, novosDados);
        return ResponseEntity.ok().body(funcionario);
    }

    @PatchMapping(value = "/inativar/{id}")
    public ResponseEntity<Void> inativar(@PathVariable int id){
        funcionarioService.inativarFuncionario(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/ativar/{id}")
    public ResponseEntity<Void> ativar(@PathVariable int id){
        funcionarioService.ativarFuncionario(id);
        return ResponseEntity.noContent().build();
    }

}
