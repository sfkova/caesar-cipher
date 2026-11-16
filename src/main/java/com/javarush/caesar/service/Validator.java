package com.javarush.caesar.service;

import com.javarush.caesar.exception.CipherException;

import java.nio.file.Files;

import java.nio.file.InvalidPathException;

import java.nio.file.Path;

import java.nio.file.Paths;

public class Validator {

    public boolean isValidKey(int key, char[] alphabet) throws CipherException {
            for (int i = 0; i < alphabet.length; i++) {
                if (key < 0 || key >= alphabet.length) {
                    throw new CipherException("Введённое значение не входит в диапазон допустимых." +
                            "Введите число от 0 до " + (alphabet.length - 1) + " включительно");
                }
        }
            return true;
    }

    public boolean isFileExists(String filePath) throws  CipherException {
        if (filePath == null || filePath.isEmpty()) {
            throw new CipherException("Путь к файлу не указан");
        }
        Path path;
        try {
            path = Paths.get(filePath);
        } catch (InvalidPathException e) {
            throw new CipherException("Недопустимый путь: " + filePath, e);
        }
        if (!Files.exists(path)) {
            throw new CipherException("Файл не найден: " + path.toAbsolutePath());
        }
        if (!Files.isRegularFile(path)) {
            throw new CipherException("Указанный путь не является файлом:" + path.toAbsolutePath());
        }
        return true;
    }
}

