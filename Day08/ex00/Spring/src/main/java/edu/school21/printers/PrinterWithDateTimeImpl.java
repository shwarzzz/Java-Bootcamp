package edu.school21.printers;

import edu.school21.renderers.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        renderer.outputToConsole(LocalDateTime.now() + " " + message);
    }
}
