package raf.microservice.components.userservice.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    public NotFoundException(String message) {
        super(message, ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
