package com.campuslab.platform.shared.application.results;

public sealed interface Result<T> permits Success, Failure {

    boolean isSuccess();
    default boolean isFailure() {
        return !isSuccess();
    }
}
