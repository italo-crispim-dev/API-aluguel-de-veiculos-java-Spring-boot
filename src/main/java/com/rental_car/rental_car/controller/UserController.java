package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.User;
import com.rental_car.rental_car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User add(@RequestBody User user){
        return userService.add(user);
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable int id){
        return userService.findById(id);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id){
        userService.delete(id);
    }

    @PutMapping(value = "/{id}")
    public User update(@PathVariable int id, @RequestBody User user){
        return userService.update(id, user);
    }

}
