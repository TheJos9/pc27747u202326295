package com.campuslab.platform.shared.application.results;

public record Failure<T>(ErrorType errorType, String messageKey, Object... args) implements Result<T> {
    @Override
    public boolean isSuccess() {
        return false;
    }

    public enum ErrorType {
        VALIDATION_ERROR,
        CONFLICT,
        INTERNAL_ERROR
    }
}
