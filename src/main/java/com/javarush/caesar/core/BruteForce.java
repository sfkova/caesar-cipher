package com.javarush.caesar.core;

import com.javarush.caesar.exception.CipherException;

import java.util.Set;

public class BruteForce {

    private static final Set<String> COMMON_WORDS = Set.of("и", "а", "в", "к", "с", "не", "за", "но", "то", "по", "он",
            "она", "оно", "они", "это", "что", "как");

    public String decryptByBruteForce(String encryptedText, char[] alphabet) throws CipherException{
        Cipher cipher = new Cipher(alphabet);

        String bestText = null;
        int bestScore = Integer.MIN_VALUE;

        for (int key = 1; key < alphabet.length; key++) {
            String candidate;
            try {
                candidate = cipher.decrypt(encryptedText, key);
            } catch (CipherException e) {
                continue;
            }
            int score = evaluate(candidate);
            if (score > bestScore) {
                bestScore = score;
                bestText = candidate;
            }
        }
        if (bestText == null) {
            throw new CipherException("Не удалось подобрать ключ");
        }
        return bestText;
        }
    private int evaluate(String text) {
        int score = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            char c = text.charAt(i);
            if ((c == '.' || c == ',') && text.charAt(i + 1) == ' ') {
                score += 2;
            }
        }
        for (String word : text.split("\\s+")) {
            String clean = word.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
            if (clean.isEmpty()) {
                continue;
            }
            if (clean.length() > 20) {
                score -= 5; // слишком длинное «слово»
            }
            if (COMMON_WORDS.contains(clean)) {
                score += 3;
            }
        }
        return score;
    }
}
