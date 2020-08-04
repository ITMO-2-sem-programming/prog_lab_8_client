package ru.itmo.core.common.exchange.response;



public class IllegalResponseException extends RuntimeException {


    public IllegalResponseException() {}

    public IllegalResponseException(String message) {
        super(message);
    }
}
