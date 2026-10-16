package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Veiculo;
import com.rental_car.rental_car.repository.VeiculoRepository;
import com.rental_car.rental_car.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public Veiculo add(@RequestBody Veiculo veiculo){
        Veiculo result = veiculoService.add(veiculo);
        return result;
    }

    @GetMapping
    public List<Veiculo> allFind(){
        List<Veiculo> result = veiculoService.allFind();
        return result;
    }

    @GetMapping(value = "/{id}")
    public Veiculo findById(@PathVariable int id){
        Veiculo result = veiculoService.findById(id);
        return result;
    }

    @PutMapping(value = "/{id}")
    public Veiculo update(@PathVariable int id, @RequestBody Veiculo novosDados){
        Veiculo veiculo = veiculoService.update(id, novosDados);
        return veiculo;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id){
        veiculoService.deleteById(id);
    }
}
