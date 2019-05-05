package com.qaengine.exceptions;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
    public PermissionDeniedException() {
        super("Permission denied");
    }
}
