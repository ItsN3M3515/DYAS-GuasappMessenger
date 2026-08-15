package edu.unisabana.dyas.patterns.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntegrationFilterTest {

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
    public void dangerousStopsBeforeLengthOrRate() {
        // Composición: Rate -> Length -> Dangerous -> MessagingClient
        MessageSender client = new MessagingClient();
        client = new RateLimitFilter(client);
        client = new LengthFilter(client);
        client = new DangerousContentFilter(client);

        client.sendMessage("##{./exec(rm /* -r)}");
        // Only the dangerous message must be logged
        assertEquals("Mensaje bloqueado debido a contenido peligroso", out.toString().trim());
    }
}
