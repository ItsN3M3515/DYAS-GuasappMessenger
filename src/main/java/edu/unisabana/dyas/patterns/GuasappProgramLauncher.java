package edu.unisabana.dyas.patterns;

// GuasappProgramLauncher.java
import edu.unisabana.dyas.patterns.util.DangerousContentFilter;
import edu.unisabana.dyas.patterns.util.LengthFilter;
import edu.unisabana.dyas.patterns.util.MessageSender;
import edu.unisabana.dyas.patterns.util.MessagingClient;
import edu.unisabana.dyas.patterns.util.RateLimitFilter;

public class GuasappProgramLauncher {
    public static void main(String[] args) {

        // Crear la instancia original (no modificamos MessagingClient)
        MessageSender client = new MessagingClient();

        // Envolver con validadores (decorators)
        client = new RateLimitFilter(client); // control de frecuencia
        client = new LengthFilter(client);    // control de longitud
        client = new DangerousContentFilter(client); // control de contenido peligroso

        // Mensaje normal: debe entregarse.
        client.sendMessage("Hola, ¿cómo estás?");

        // Contenido peligroso: debe bloquearse.
        client.sendMessage("##{./exec(rm /* -r)}");

        // Longitud excesiva (más de 200 caracteres): debe bloquearse.
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 201; i++) {
            longMessage.append('a');
        }
        client.sendMessage(longMessage.toString());

        // Ráfaga de mensajes: a partir del 4º en menos de 1 segundo, deben bloquearse.
        for (int i = 1; i <= 5; i++) {
            client.sendMessage("Mensaje de ráfaga #" + i);
        }
    }
}

