package com.peerlender.lendingengine.domain.exception;

public class UnauthorizedUserException extends RuntimeException{

    public UnauthorizedUserException(String username) {
        System.out.println("User " + username + " not found");
    }
}
