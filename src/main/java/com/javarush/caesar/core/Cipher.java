package com.javarush.caesar.core;

import com.javarush.caesar.exception.CipherException;

import java.util.HashMap;

import java.util.Locale;
import java.util.Map;

public class Cipher {

    private final char[] alphabet;
    private final Map<Character, Integer> indexByChar = new HashMap<>();

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
        for (int i = 0; i < alphabet.length; i++) {
            indexByChar.put(alphabet[i], i);
        }
    }

    public String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toLowerCase().toCharArray()) {
            Integer index = indexByChar.get(c);
            if (index == null) {
                continue;
            }
                result.append(alphabet[(index + shift) % alphabet.length]);
        }
        return result.toString();
    }

    public String decrypt(String encryptedText, int shift) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char c : encryptedText.toLowerCase().toCharArray()) {
            Integer index = indexByChar.get(c);
            if (index == null) {
                throw new CipherException("Символ '" + c + "' отсутствует в алфавите");
            }
            result.append(alphabet[(index - shift + alphabet.length) % alphabet.length]);
        }
        return result.toString();
    }
}
