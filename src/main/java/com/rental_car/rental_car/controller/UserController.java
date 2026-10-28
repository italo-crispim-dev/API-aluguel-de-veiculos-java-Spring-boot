package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Cliente;
import com.rental_car.rental_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Cliente> add(@RequestBody Cliente cliente){
        return ResponseEntity.status(201).body(userService.add(cliente));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable int id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@PathVariable int id, @RequestBody Cliente cliente){
        return ResponseEntity.ok().body(userService.update(id, cliente));
    }

}
