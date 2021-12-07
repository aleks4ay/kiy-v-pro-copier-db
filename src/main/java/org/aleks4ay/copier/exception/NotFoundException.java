package org.aleks4ay.copier.exception;

import java.io.IOException;

public class NotFoundException extends IOException{
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message + super.toString();
    }
}
