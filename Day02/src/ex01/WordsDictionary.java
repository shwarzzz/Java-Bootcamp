package ex01;

import java.util.Map;
import java.util.Set;

public class WordsDictionary {
    private final Set<String> wordsDictionary;
    private final Map<String, Integer> firstDictionary;
    private final Map<String, Integer> secondDictionary;

    public WordsDictionary(Set<String> wordsDictionary,
                           Map<String, Integer> firstDictionary,
                           Map<String, Integer> secondDictionary) {
        this.wordsDictionary = wordsDictionary;
        this.firstDictionary = firstDictionary;
        this.secondDictionary = secondDictionary;
    }

    public Set<String> getWordsDictionary() {
        return wordsDictionary;
    }

    public Map<String, Integer> getFirstDictionary() {
        return firstDictionary;
    }

    public Map<String, Integer> getSecondDictionary() {
        return secondDictionary;
    }
}