package edu.unisabana.dyas.patterns.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LengthFilterTest {

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
    public void blocksLongMessage() {
        MessageSender client = new LengthFilter(new MessagingClient(), 200);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 201; i++) sb.append('a');
        client.sendMessage(sb.toString());
        assertEquals("Mensaje bloqueado por exceder la longitud máxima permitida", out.toString().trim());
    }

    @Test
    public void forwardsShortMessage() {
        MessageSender client = new LengthFilter(new MessagingClient(), 200);
        client.sendMessage("corto");
        assertEquals("Sending message: corto", out.toString().trim());
    }
}
