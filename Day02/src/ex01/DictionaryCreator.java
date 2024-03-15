package ex01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DictionaryCreator {
    private static DictionaryCreator instance;

    private DictionaryCreator() {
    }

    public static DictionaryCreator getInstance() {
        if (instance == null) {
            instance = new DictionaryCreator();
        }
        return instance;
    }

    public WordsDictionary createDictionary(String firstFilePath,
                                            String secondFilePath) {
        Set<String> dictionary = new HashSet<>();
        Map<String, Integer> firstDictionary =
                addWordsToDictionary(dictionary, firstFilePath);
        Map<String, Integer> secondDictionary =
                addWordsToDictionary(dictionary, secondFilePath);
        return new WordsDictionary(dictionary, firstDictionary,
                secondDictionary);
    }

    private Map<String, Integer> addWordsToDictionary(Set<String> dictionary, String filePath) {
        Map<String, Integer> countingWords = new HashMap<>();
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.trim().split("[^\\p{IsLatin}\\p{IsCyrillic}]+");
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    dictionary.add(word);
                    countingWords.put(word,
                            countingWords.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return countingWords;
    }
}
