package edu.school21.printers;

import edu.school21.renderers.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private final Renderer renderer;
    private String prefix = "";

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String message) {
        renderer.outputToConsole(prefix + " " + message);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
