package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.VeiculoRequestDTO;
import com.rental_car.rental_car.dto.VeiculoResponseDTO;
import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.VeiculoRepository;
import com.rental_car.rental_car.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<VeiculoResponseDTO> add(@Valid @RequestBody VeiculoRequestDTO veiculo){
        VeiculoResponseDTO result = veiculoService.add(veiculo);
        return ResponseEntity.status(201).body(result);
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> findALl(){
        List<VeiculoResponseDTO> result = veiculoService.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponseDTO> findById(@PathVariable int id){
        VeiculoResponseDTO result = veiculoService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponseDTO> update(@PathVariable int id, @Valid @RequestBody VeiculoRequestDTO novosDados){
        VeiculoResponseDTO veiculo = veiculoService.update(id, novosDados);
        return ResponseEntity.ok().body(veiculo);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
