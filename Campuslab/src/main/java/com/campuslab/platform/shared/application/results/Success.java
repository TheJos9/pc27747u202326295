package com.campuslab.platform.shared.application.results;

public record Success<T>(T value) implements Result<T> {
    @Override
    public boolean isSuccess() {
        return true;
    }
}
