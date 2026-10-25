package com.rental_car.rental_car.infra;

import com.rental_car.rental_car.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(VeiculoIndisponivelException.class)
    private ResponseEntity<RestErrorMessage> veiculoIndisponivelHandler(VeiculoIndisponivelException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> veiculoNaoEncontradoHandler(VeiculoNaoEncontradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(PlacaJaCadastradaException.class)
    private ResponseEntity<RestErrorMessage> placaJaCadastradaHandler(PlacaJaCadastradaException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(VeiculoNaoPodeSerAlteradoException.class)
    private ResponseEntity<RestErrorMessage> veiculoNaoPodeSerAlteradoHandler(VeiculoNaoPodeSerAlteradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(VeiculoNaoPodeSerApagadoException.class)
    private ResponseEntity<RestErrorMessage> veiculoNaoPodeSerApagadoHandler(VeiculoNaoPodeSerApagadoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(AluguelNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> aluguelNaoEncontradoHandler(AluguelNaoEncontradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(AluguelNaoPodeSerAlteradoException.class)
    private ResponseEntity<RestErrorMessage> aluguelNaoPodeSerAlteradoHandler(AluguelNaoPodeSerAlteradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(AluguelNaoPodeSerFinalizadoException.class)
    private ResponseEntity<RestErrorMessage> aluguelNaoPodeSerFinalizadoHandler(AluguelNaoPodeSerFinalizadoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(UserNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> UserNaoEncontradoHandler(UserNaoEncontradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(FuncionarioNaoEncontradoException.class)
    private ResponseEntity<RestErrorMessage> FuncionarioNaoEncontradoHandler(FuncionarioNaoEncontradoException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }


}
