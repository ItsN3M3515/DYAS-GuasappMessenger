package edu.unisabana.dyas.patterns.util;

import java.util.Optional;

// Validador que detecta contenido peligroso (##{...})
public class DangerousContentValidator implements MessageValidator {

    @Override
    public Optional<String> validate(String message) {
        if (message != null && message.contains("##{")) {
            return Optional.of("Mensaje bloqueado debido a contenido peligroso");
        }
        return Optional.empty();
    }
}
