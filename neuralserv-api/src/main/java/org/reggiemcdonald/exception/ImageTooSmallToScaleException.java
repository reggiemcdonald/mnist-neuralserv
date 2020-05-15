package org.reggiemcdonald.exception;

public class ImageTooSmallToScaleException extends ScalingServiceException {
    public ImageTooSmallToScaleException() {
        super("The dimensions of the image must be at least 28X28 pixels");
    }
}
