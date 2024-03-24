package ex01;

import java.util.Map;
import java.util.Set;

public class CosineMeasureCalculator {
    public CosineMeasureCalculator() {

    }

    public double calculateSimilarity(WordsDictionary dictionary) {
        return calculateNumerator(dictionary) /
                calculateDenominator(dictionary);
    }

    private double calculateNumerator(WordsDictionary dictionary) {
        Set<String> wordsDictionary = dictionary.getWordsDictionary();
        Map<String, Integer> firstDictionary = dictionary.getFirstDictionary();
        Map<String, Integer> secondDictionary = dictionary.getSecondDictionary();
        double numerator = 0;
        for (String word : wordsDictionary) {
            numerator += secondDictionary.getOrDefault(word, 0) *
                    firstDictionary.getOrDefault(word, 0);
        }
        return numerator;
    }

    private double calculateDenominator(WordsDictionary dictionary) {
        return calculatePowerSum(dictionary.getFirstDictionary()) *
                calculatePowerSum(dictionary.getSecondDictionary());
    }

    private double calculatePowerSum(Map<String, Integer> fileDictionary) {
        double result = 0;
        for (Map.Entry<String, Integer> entry : fileDictionary.entrySet()) {
            result += entry.getValue() * entry.getValue();
        }
        return Math.sqrt(result);
    }
}
