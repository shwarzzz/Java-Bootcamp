package edu.school21.preprocessors;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String process(String data) {
        return data.toUpperCase();
    }
}
