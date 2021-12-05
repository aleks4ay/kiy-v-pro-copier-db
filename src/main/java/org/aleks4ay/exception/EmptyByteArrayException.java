package org.aleks4ay.exception;

import java.io.IOException;

public class EmptyByteArrayException extends IOException{
    @Override
    public String toString() {
        return "Byte Array is empty. " + super.toString();
    }
}
