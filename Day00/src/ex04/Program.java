package ex04;

import java.util.Scanner;

public class Program {
    static final int MAX_NUMBER_OF_CHARACTERS = 999;
    static final int MAX_CODE_VALUE = 65535;
    static final int MAX_HEIGHT_OF_CHART = 10;

    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        String str = inp.nextLine();
        inp.close();
        char[] charArray = str.toCharArray();
        if (charArray.length == 0) {
            return;
        }
        short[] numberOfChar = countTheSymbols(charArray);
        printTop(numberOfChar, makeTopOfChar(numberOfChar, charArray));
    }

    public static short[] countTheSymbols(char[] str) {
        short[] result = new short[MAX_CODE_VALUE];
        for (char c : str) {
            result[c]++;
            if (result[c] > MAX_NUMBER_OF_CHARACTERS) {
                System.out.println("Illegal argument");
                System.exit(-1);
            }
        }
        return result;
    }

    public static int[] makeTopOfChar(short[] numberOfChar, char[] str) {
        int[] topOfChar = new int[MAX_HEIGHT_OF_CHART];
        for (int i = 0; i < topOfChar.length; i++) {
            for (char c : str) {
                if (numberOfChar[c] > numberOfChar[topOfChar[i]]) {
                    if (i == 0 || (c != topOfChar[i - 1] && numberOfChar[c] < numberOfChar[topOfChar[i - 1]])) {
                        topOfChar[i] = c;
                    } else if (numberOfChar[c] == numberOfChar[topOfChar[i - 1]] && c > topOfChar[i - 1]) {
                        topOfChar[i] = c;
                    }
                } else if (numberOfChar[topOfChar[i]] == numberOfChar[c] && topOfChar[i] != c) {
                    if (i == 0 || c != topOfChar[i - 1]) {
                        if (i != 0 && numberOfChar[topOfChar[i - 1]] == numberOfChar[c] && c < topOfChar[i - 1]) {
                            continue;
                        }
                        topOfChar[i] = c < topOfChar[i] ? c : topOfChar[i];
                    }
                }
            }
        }
        return topOfChar;
    }

    public static void printTop(short[] str, int[] topChart) {
        int max = str[topChart[0]];
        byte maxHeight = (byte) max < 10 ? (byte) max : 10;
        byte totalLines = (byte) (2 + maxHeight);
        byte[] graphs = new byte[MAX_HEIGHT_OF_CHART];
        for (int i = 0; i < MAX_HEIGHT_OF_CHART; i++) {
            if (max <= 10) {
                graphs[i] = (byte) str[topChart[i]];
            } else {
                graphs[i] = (byte) (str[topChart[i]] * 10 / max);
            }
        }
        for (int i = 0; i < totalLines; i++) {
            for (int j = 0; j < MAX_HEIGHT_OF_CHART; j++) {
                if (topChart[j] != 0) {
                    if (i + graphs[j] + 2 == totalLines) {
                        System.out.printf("%3d", str[topChart[j]]);
                    } else if (i == totalLines - 1) {
                        System.out.printf("%3c", topChart[j]);
                    } else if (i + graphs[j] >= maxHeight) {
                        System.out.printf("%3c", '#');
                    }
                    if (j != MAX_HEIGHT_OF_CHART - 1 && topChart[j + 1] != 0 && i + graphs[j + 1] >= maxHeight) {
                        System.out.printf("%c", ' ');
                    }
                }
            }
            System.out.println();
        }
    }
}