package edu.unisabana.dyas.patterns.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RateLimitFilterTest {

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
    public void blocksWhenTooManyMessages() {
        MessageSender client = new RateLimitFilter(new MessagingClient(), 3, 1000L);
        // send 4 quick messages
        for (int i = 1; i <= 4; i++) {
            client.sendMessage("m" + i);
        }
        String output = out.toString().trim();
        // should contain three sends and a rate-limit message
        assertTrue(output.contains("Sending message: m1"));
        assertTrue(output.contains("Sending message: m2"));
        assertTrue(output.contains("Sending message: m3"));
        assertTrue(output.contains("Mensaje bloqueado por exceso de frecuencia de envío"));
    }
}
