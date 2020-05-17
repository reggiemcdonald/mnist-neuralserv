package org.reggiemcdonald.exception;

/**
 * Exception thrown when the image to be scaled is below the required size
 */

public class TooSmallToScaleException extends ScalingServiceException {
    public TooSmallToScaleException() {
        super("The dimensions of the image must be at least 28X28 pixels");
    }
}
