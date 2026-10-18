package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Aluguel")
public class AluguelContoller {

    @Autowired
    private AluguelService aluguelService;

    @PostMapping
    public ResponseEntity<Aluguel> add(@RequestBody Aluguel aluguel){
        return ResponseEntity.status(201).body(aluguelService.add(aluguel));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Aluguel> findById(@PathVariable int id) {
        return ResponseEntity.ok().body(aluguelService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Aluguel>> findAll() {
        return ResponseEntity.ok().body(aluguelService.findAll());
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Aluguel> finaliza(@PathVariable int id){
        return ResponseEntity.ok().body(aluguelService.finaliza(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Aluguel> update(@PathVariable int id, @RequestBody Aluguel novosDados){
        return ResponseEntity.ok().body(aluguelService.update(id, novosDados));
    }


}
