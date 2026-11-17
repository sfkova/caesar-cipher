package com.javarush.caesar.service;

import com.javarush.caesar.exception.CipherException;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.List;

public class FileManager {

    private static final String BOM = "\\uFEFF";

    public List<String> readFile(String filePath) throws CipherException {
        Path path = Paths.get(filePath);
        try {
            List<String> lines = new ArrayList<>(Files.readAllLines(path));
            if (!lines.isEmpty() && lines.get(0).startsWith(BOM)) {
                lines.set(0, lines.get(0).substring(1));
            }
            return lines;
        }
        catch (IOException e) {
            throw new CipherException("Не удалось прочитать файл:" + filePath + ", нужна кодировка UTF-8.", e);
        }
    }

    public void writeFile(List <String> content, String input, String output) throws CipherException {
        Path in = Paths.get(input);
        Path out = Paths.get(output);
        try {
            if (Files.exists(in) && Files.exists(out) && Files.isSameFile(in, out)) {
                throw new CipherException("Результат нельзя записать в исходный файл");
            }
            Path parent = out.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.write(out, content);
            } catch (IOException e) {
            throw new CipherException("Не удалось записать файл:" + output, e);
        }
    }
}
