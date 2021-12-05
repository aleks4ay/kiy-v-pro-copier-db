package org.aleks4ay.exception;

import java.io.IOException;

public class CannotReadDataFromByteArrayException extends IOException{
    @Override
    public String toString() {
        return "Can not read all data from Byte Array correctly. " + super.toString();
    }
}
