package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.model.Manutencao;
import com.rental_car.rental_car.service.ManutencaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manutencoes")
public class ManutencaoController {
    @Autowired
    private ManutencaoService manutencaoService;

    @PostMapping
    public ResponseEntity<Manutencao> adicionar(@RequestBody Manutencao manutencao){
        Manutencao novaManutencao = manutencaoService.add(manutencao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaManutencao);
    }

    @GetMapping
    public ResponseEntity<List<Manutencao>> listarTodos(){
        return ResponseEntity.ok(manutencaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manutencao> buscarPorId(@PathVariable int id){
        Manutencao encontrada = manutencaoService.buscarPorId(id);
        return ResponseEntity.ok(encontrada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Manutencao> atualizar(@PathVariable int id, @RequestBody Manutencao dadosAtualizados){
            Manutencao atualizada = manutencaoService.atualizar(id, dadosAtualizados);
            return ResponseEntity.ok(atualizada);
    }

    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<Manutencao> finalizar(@PathVariable int id){
        Manutencao finalizada = manutencaoService.finalizar(id);
        return ResponseEntity.ok(finalizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        manutencaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
