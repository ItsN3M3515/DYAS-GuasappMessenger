package edu.unisabana.dyas.patterns.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// Implementación proxy que aplica validadores antes de delegar en el sujeto real.
public class MessageProxy implements MessageSender {

    private final MessageSender realSender;
    private final List<MessageValidator> validators;

    public MessageProxy(MessageSender realSender, List<MessageValidator> validators) {
        this.realSender = realSender;
        this.validators = validators;
    }

    public MessageProxy(MessageSender realSender, MessageValidator... validators) {
        this(realSender, Arrays.asList(validators));
    }

    @Override
    public void sendMessage(String message) {
        for (MessageValidator v : validators) {
            Optional<String> blocked = v.validate(message);
            if (blocked.isPresent()) {
                System.out.println(blocked.get());
                return;
            }
        }
        realSender.sendMessage(message);
    }
}
