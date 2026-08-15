package edu.unisabana.dyas.patterns.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DangerousContentFilterTest {

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
    public void blocksDangerousContent() {
        MessageSender client = new DangerousContentFilter(new MessagingClient());
        client.sendMessage("##{./exec(rm /* -r)}");
        assertEquals("Mensaje bloqueado debido a contenido peligroso", out.toString().trim());
    }

    @Test
    public void forwardsSafeMessage() {
        MessageSender client = new DangerousContentFilter(new MessagingClient());
        client.sendMessage("Hola seguro");
        assertEquals("Sending message: Hola seguro", out.toString().trim());
    }
}
