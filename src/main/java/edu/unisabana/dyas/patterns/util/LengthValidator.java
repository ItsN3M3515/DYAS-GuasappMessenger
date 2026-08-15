package edu.unisabana.dyas.patterns.util;

import java.util.Optional;

// Validador de longitud máxima
public class LengthValidator implements MessageValidator {

    private final int maxLength;

    public LengthValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    public LengthValidator() {
        this(200);
    }

    @Override
    public Optional<String> validate(String message) {
        if (message != null && message.length() > maxLength) {
            return Optional.of("Mensaje bloqueado por exceder la longitud máxima permitida");
        }
        return Optional.empty();
    }
}
