package edu.unisabana.dyas.patterns.util;

// Filtro que impide el envío de mensajes demasiado largos (>200 caracteres)
public class LengthFilter implements MessageSender {

    private final MessageSender next;
    private final int maxLength;

    public LengthFilter(MessageSender next, int maxLength) {
        this.next = next;
        this.maxLength = maxLength;
    }

    public LengthFilter(MessageSender next) {
        this(next, 200);
    }

    @Override
    public void sendMessage(String message) {
        if (message != null && message.length() > maxLength) {
            System.out.println("Mensaje bloqueado por exceder la longitud máxima permitida");
            return;
        }
        next.sendMessage(message);
    }
}
