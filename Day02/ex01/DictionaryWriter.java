package ex01;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DictionaryWriter {
    private static final String DICTIONARY_FILE = "dictionary.txt";

    public DictionaryWriter() {

    }

    public void writeDictionaryToFile(WordsDictionary dictionary) {
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(DICTIONARY_FILE))) {
            for (String word : dictionary.getWordsDictionary()) {
                out.write(word + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
}
