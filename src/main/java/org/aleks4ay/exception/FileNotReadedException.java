package org.aleks4ay.exception;

import java.io.IOException;

public class FileNotReadedException extends IOException{
    @Override
    public String toString() {
        return "Can't read data from file 1S. " + super.toString();
    }
}
