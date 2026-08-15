package edu.unisabana.dyas.patterns.util;

import java.util.ArrayDeque;
import java.util.Deque;

// Filtro simple de tasa: permite hasta 3 mensajes por ventana de 1 segundo.
public class RateLimitFilter implements MessageSender {

    private final MessageSender next;
    private final Deque<Long> timestamps = new ArrayDeque<>();
    private final long windowMillis;
    private final int maxMessages;

    public RateLimitFilter(MessageSender next) {
        this(next, 3, 1000L);
    }

    public RateLimitFilter(MessageSender next, int maxMessages, long windowMillis) {
        this.next = next;
        this.maxMessages = maxMessages;
        this.windowMillis = windowMillis;
    }

    @Override
    public synchronized void sendMessage(String message) {
        long now = System.currentTimeMillis();

        // Eliminar timestamps fuera de la ventana
        while (!timestamps.isEmpty() && (now - timestamps.peekFirst()) >= windowMillis) {
            timestamps.removeFirst();
        }

        // Si ya hubo maxMessages en la ventana, bloquear
        if (timestamps.size() >= maxMessages) {
            System.out.println("Mensaje bloqueado por exceso de frecuencia de envío");
            return;
        }

        timestamps.addLast(now);
        next.sendMessage(message);
    }
}
