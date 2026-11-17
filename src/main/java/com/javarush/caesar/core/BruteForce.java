package com.javarush.caesar.core;

import com.javarush.caesar.exception.CipherException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BruteForce {

    private static final Set<String> COMMON_WORDS = Set.of("и", "а", "в", "к", "с", "не", "за", "но", "то", "по", "он",
            "она", "оно", "они", "это", "что", "как");

    private static final int MAX_WORD_LENGTH = 20;

    public int decryptByBruteForce(List<String> lines, char[] alphabet) throws CipherException {
        Cipher cipher = new Cipher(alphabet);

        int bestKey = -1;
        int bestScore = Integer.MIN_VALUE;

        for (int key = 1; key < alphabet.length; key++) {
            List<String> decrypted = new ArrayList<>();
            for (String line : lines) {
                decrypted.add(cipher.decrypt(line, key));
            }
            int score = evaluate(String.join("\n", decrypted));
            if (score > bestScore) {
                bestScore = score;
                bestKey = key;
            }
        }

        if (bestKey == -1) {
            throw new CipherException("Не удалось подобрать ключ");
        }
        return bestKey;
    }

    private int evaluate(String text) {
        int score = 0;

        // пробел после точки или запятой
        for (int i = 0; i < text.length() - 1; i++) {
            char c = text.charAt(i);
            if ((c == '.' || c == ',') && text.charAt(i + 1) == ' ') {
                score += 2;
            }
        }

        for (String word : text.split("\\s+")) {
            String clean = word.replaceAll("[^а-яё]", "");
            if (clean.isEmpty()) {
                continue;
            }
            if (clean.length() > MAX_WORD_LENGTH) {
                score -= 5;
            }
            if (COMMON_WORDS.contains(clean)) {
                score += 3;
            }
        }
        return score;
    }
}
