package edu.unisabana.dyas.patterns.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

// Validador de tasa: hasta N mensajes por ventana (en ms)
public class RateLimitValidator implements MessageValidator {

    private final Deque<Long> timestamps = new ArrayDeque<>();
    private final int maxMessages;
    private final long windowMillis;

    public RateLimitValidator(int maxMessages, long windowMillis) {
        this.maxMessages = maxMessages;
        this.windowMillis = windowMillis;
    }

    public RateLimitValidator() {
        this(3, 1000L);
    }

    @Override
    public synchronized Optional<String> validate(String message) {
        long now = System.currentTimeMillis();
        while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) >= windowMillis) {
            timestamps.removeFirst();
        }
        if (timestamps.size() >= maxMessages) {
            return Optional.of("Mensaje bloqueado por exceso de frecuencia de envío");
        }
        timestamps.addLast(now);
        return Optional.empty();
    }
}
