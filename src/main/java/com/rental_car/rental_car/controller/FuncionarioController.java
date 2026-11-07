package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.FuncionarioRequestDto;
import com.rental_car.rental_car.dto.FuncionarioResponseDto;
import com.rental_car.rental_car.model.Funcionario;
import com.rental_car.rental_car.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
@Validated
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<FuncionarioResponseDto> add(@Valid  @RequestBody FuncionarioRequestDto funcionario){
        FuncionarioResponseDto result = funcionarioService.add(funcionario);
        return ResponseEntity.status(201).body(result);

    }

    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDto>> findAll(){
        List<Funcionario> funcionarios = funcionarioService.findAll();
        List<FuncionarioResponseDto> response = funcionarios.stream().map(FuncionarioResponseDto::fromEntity).toList();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDto> findById(@PathVariable int id){
        Funcionario funcionario = funcionarioService.findById(id);

        FuncionarioResponseDto response = FuncionarioResponseDto.fromEntity(funcionario);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDto> update(@PathVariable int id, @Valid @RequestBody FuncionarioRequestDto novosDados){
        FuncionarioResponseDto funcionario = funcionarioService.update(id, novosDados);
        return ResponseEntity.ok().body(funcionario);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        funcionarioService.deleteByID(id);
        return ResponseEntity.noContent().build();
    }

}
