package ru.itmo.util.mapper.exception;


public class InvalidMappingRangeException extends MapperException {

    public InvalidMappingRangeException() {
    }

    public InvalidMappingRangeException(String message) {
        super(message);
    }

    public InvalidMappingRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMappingRangeException(Throwable cause) {
        super(cause);
    }

    public InvalidMappingRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
