package edu.unisabana.dyas.patterns.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MessageProxyTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void proxyAppliesValidatorsInOrder() {
        MessageSender real = new MessagingClient();
        MessageSender proxy = new MessageProxy(real,
                new DangerousContentValidator(),
                new LengthValidator(),
                new RateLimitValidator()
        );

        proxy.sendMessage("Hola seguro");
        proxy.sendMessage("##{./exec(rm /* -r)}");

        String output = out.toString().trim();
        assertTrue(output.contains("Sending message: Hola seguro"));
        assertTrue(output.contains("Mensaje bloqueado debido a contenido peligroso"));
    }
}
