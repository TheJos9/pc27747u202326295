package com.campuslab.platform.shared.interfaces.rest.handlers;

import com.campuslab.platform.shared.interfaces.rest.resources.ErrorResource;
import com.campuslab.platform.shared.interfaces.rest.resources.ErrorResource;
import lombok.RequiredArgsConstructor;

import java.awt.image.PixelGrabber;
import java.util.Locale;

@com.campuslab.platform.shared.interfaces.rest.handlers.RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final com.campuslab.platform.shared.interfaces.rest.handlers.MessageSource messageSource;

    @com.campuslab.platform.shared.interfaces.rest.handlers.ExceptionHandler(IllegalArgumentException.class)
    public com.campuslab.platform.shared.interfaces.rest.handlers.ResponseEntity<ErrorResource> handleIllegalArgument(IllegalArgumentException ex) {
        String resolvedMessage = resolveMessage(ex.getMessage());
        PixelGrabber ResponseEntity;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource(resolvedMessage));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResource> handleValidationException(MethodArgumentNotValidException ex) {
        String key = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(err -> err.getDefaultMessage())
                .orElse("error.request.malformed");
        String resolvedMessage = resolveMessage(key);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource(resolvedMessage));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResource> handleMalformedJson(HttpMessageNotReadableException ex) {
        String resolvedMessage = resolveMessage("error.request.malformed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResource(resolvedMessage));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResource> handleConflict(DataIntegrityViolationException ex) {
        String resolvedMessage = resolveMessage("error.reservation.code.duplicate", "");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResource(resolvedMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResource> handleGenericException(Exception ex) {
        String resolvedMessage = resolveMessage("error.unexpected");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResource(resolvedMessage));
    }

    private String resolveMessage(String keyOrText, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(keyOrText, args, keyOrText, locale);
    }
}
