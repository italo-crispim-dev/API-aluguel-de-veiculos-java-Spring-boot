package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.AluguelResponseDto;
import com.rental_car.rental_car.dto.AluguelResquestDto;
import com.rental_car.rental_car.dto.AluguelUpdateDto;
import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.service.AluguelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/alugueis")
public class AluguelContoller {

    @Autowired
    private AluguelService aluguelService;

    @PostMapping
    public ResponseEntity<AluguelResponseDto> add(@Valid @RequestBody AluguelResquestDto aluguel){
        return ResponseEntity.status(201).body(aluguelService.add(aluguel));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AluguelResponseDto> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(aluguelService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AluguelResponseDto>> findAll() {
        return ResponseEntity.ok().body(aluguelService.findAll());
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AluguelResponseDto> finaliza(@PathVariable int id){
        return ResponseEntity.ok().body(aluguelService.finaliza(id));
    }

    @PatchMapping(value = "/atualiza/{id}")
    public ResponseEntity<AluguelResponseDto> update(@PathVariable int id, @Valid @RequestBody AluguelUpdateDto novosDados){
        return ResponseEntity.ok().body(aluguelService.update(id, novosDados));
    }


}
