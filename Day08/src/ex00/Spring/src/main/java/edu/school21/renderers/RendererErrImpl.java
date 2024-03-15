package edu.school21.renderers;

import edu.school21.preprocessors.PreProcessor;

public class RendererErrImpl implements Renderer {
    private final PreProcessor preProcessor;

    public RendererErrImpl(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    @Override
    public void outputToConsole(String message) {
        System.err.println(preProcessor.process(message));
    }
}
