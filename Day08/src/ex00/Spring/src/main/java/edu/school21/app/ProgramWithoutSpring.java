package edu.school21.app;

import edu.school21.preprocessors.PreProcessor;
import edu.school21.preprocessors.PreProcessorToUpperImpl;
import edu.school21.printers.PrinterWithPrefixImpl;
import edu.school21.renderers.Renderer;
import edu.school21.renderers.RendererErrImpl;

public class ProgramWithoutSpring {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix");
        printer.print("Hello!");
    }
}
