package edu.school21.preprocessors;

public class PreProcessorToLower implements PreProcessor {
    @Override
    public String process(String data) {
        return data.toLowerCase();
    }
}
