package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Aluguel;
import com.rental_car.rental_car.service.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Aluguel")
public class AluguelContoller {

    @Autowired
    private AluguelService aluguelService;

    @PostMapping
    public Aluguel add(@RequestBody Aluguel aluguel){
        return aluguelService.add(aluguel);
    }

    @GetMapping(value = "/{id}")
    public Aluguel findById(@PathVariable int id){
        return aluguelService.findById(id);
    }

    @GetMapping
    public List<Aluguel> findAll(){
        return aluguelService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public Aluguel delete(@PathVariable int id){
        return aluguelService.delete(id);
    }

    @PutMapping(value = "/{id}")
    public Aluguel update(@PathVariable int id, @RequestBody Aluguel novosDados){
        return aluguelService.update(id, novosDados);
    }


}
