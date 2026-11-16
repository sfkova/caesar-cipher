package com.javarush.caesar.core;

import com.javarush.caesar.exception.CipherException;

import java.util.HashMap;

import java.util.Map;

public class Cipher {

    private final char[] alphabet;
    private final Map<Character, Integer> IndexByChar = new HashMap<>();

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String text, int shift) {
        for (int i = 0; i < alphabet.length; i++) {
            IndexByChar.put(alphabet[i], i);
        }
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            Integer index = IndexByChar.get(c);
            if (index == null) {
                result.append(c);
            } else {
                result.append(alphabet[(index + shift) % alphabet.length]);
            }
        }
        return result.toString();
    }

    public String decrypt(String encryptedText, int shift) throws CipherException {
        StringBuilder result = new StringBuilder();
        for (char c : encryptedText.toCharArray()) {
            Integer index = IndexByChar.get(c);
            if (index == null) {
                throw new CipherException("Символ '" + c + "' отсутствует в алфавите");
            }
            result.append(alphabet[(index - shift + alphabet.length) % alphabet.length]);
        }
        return result.toString();
    }
}
