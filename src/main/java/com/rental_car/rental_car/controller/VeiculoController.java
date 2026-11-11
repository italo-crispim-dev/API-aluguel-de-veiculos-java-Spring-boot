package com.rental_car.rental_car.controller;

import com.rental_car.rental_car.dto.VeiculoRequestDTO;
import com.rental_car.rental_car.dto.VeiculoResponseDTO;
import com.rental_car.rental_car.repository.VeiculoRepository.MarcaAlugadaProjection;
import com.rental_car.rental_car.service.VeiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/veiculos")
@RequiredArgsConstructor
@Validated
public class VeiculoController {

    private final VeiculoService veiculoService;

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

    //consultas
    @GetMapping("/relatorio/marcas")
    public ResponseEntity<List<MarcaAlugadaProjection>> buscarMarcaMaisAlugada(){
        return ResponseEntity.ok(veiculoService.buscarMarcaMaisAlugada());
    }
}
