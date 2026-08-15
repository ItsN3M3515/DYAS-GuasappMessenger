package edu.unisabana.dyas.patterns.util;

import java.util.Optional;

/**
 * Validador de mensajes utilizado por el proxy. Devuelve un mensaje de bloqueo
 * si la validación falla, o empty() si pasa.
 */
public interface MessageValidator {
    Optional<String> validate(String message);
}
