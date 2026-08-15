package edu.unisabana.dyas.patterns.util;

// Filtro que bloquea mensajes que contienen el patrón peligroso ##{...}
public class DangerousContentFilter implements MessageSender {

    private final MessageSender next;

    public DangerousContentFilter(MessageSender next) {
        this.next = next;
    }

    @Override
    public void sendMessage(String message) {
        // Si contiene la secuencia que abre el patrón peligroso, bloquear.
        if (message != null && message.contains("##{")) {
            System.out.println("Mensaje bloqueado debido a contenido peligroso");
            return;
        }
        next.sendMessage(message);
    }
}
