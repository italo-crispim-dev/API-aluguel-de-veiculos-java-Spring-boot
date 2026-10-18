package com.rental_car.rental_car.exception;

public class UserNaoEncontradoException extends RuntimeException {
    public UserNaoEncontradoException(String message) {
        super(message);
    }
}
