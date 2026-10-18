package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.User;
import com.rental_car.rental_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user){
        return ResponseEntity.status(201).body(userService.add(user));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable int id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable int id, @RequestBody User user){
        return ResponseEntity.ok().body(userService.update(id, user));
    }

}
