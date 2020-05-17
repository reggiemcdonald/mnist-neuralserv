package org.reggiemcdonald.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NeuralNetServiceException extends Exception {
    public NeuralNetServiceException(String msg) {
        super(msg);
    }
}
