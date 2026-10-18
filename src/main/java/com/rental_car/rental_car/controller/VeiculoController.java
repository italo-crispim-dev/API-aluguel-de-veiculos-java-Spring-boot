package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.VeiculoRepository;
import com.rental_car.rental_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Veiculo> add(@RequestBody Veiculo veiculo){
        Veiculo result = veiculoService.add(veiculo);
        return ResponseEntity.status(201).body(veiculo);
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> allFind(){
        List<Veiculo> result = veiculoService.allFind();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable int id){
        Veiculo result = veiculoService.findById(id);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/{id}")
    public Veiculo update(@PathVariable int id, @RequestBody Veiculo novosDados){
        Veiculo veiculo = veiculoService.update(id, novosDados);
        return veiculo;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
