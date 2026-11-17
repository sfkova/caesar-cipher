package com.javarush.caesar;

import com.javarush.caesar.core.BruteForce;
import com.javarush.caesar.core.Cipher;
import com.javarush.caesar.exception.CipherException;
import com.javarush.caesar.service.FileManager;
import com.javarush.caesar.service.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п',
            'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    private final Scanner scanner = new Scanner(System.in);
    private final FileManager fileManager = new FileManager();
    private final Validator validator = new Validator();
    private final Cipher cipher = new Cipher(ALPHABET);
    private final BruteForce bruteForce = new BruteForce();

    public static void main(String[] args) {
        new MainApp().run();
    }

    private void run() {
        System.out.println("Шифр Цезаря: шифрование и расшифровка файлов");
        while (true) {
            printMenu();
            String choice = ask("Выберите действие: ");
            try {
                switch (choice) {
                    case "1":
                        encryptFile();
                        break;
                    case "2":
                        decryptFile();
                        break;
                    case "3":
                        bruteForceFile();
                        break;
                    case "0":
                    case "exit":
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Нет такого пункта: " + choice);
                }
            } catch (CipherException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Зашифровать файл");
        System.out.println("2. Расшифровать файл с ключом");
        System.out.println("3. Взломать перебором ключей");
        System.out.println("0. Выход (или exit)");
    }

    private void encryptFile() throws CipherException {
        String source = askSourcePath();
        String target = askTargetPath();
        int key = askKey();

        List<String> result = new ArrayList<>();
        for (String line : fileManager.readFile(source)) {
            result.add(cipher.encrypt(line, key));
        }
        fileManager.writeFile(result, source, target);
        System.out.println("Готово. Результат записан в " + target);
    }

    private void decryptFile() throws CipherException {
        String source = askSourcePath();
        String target = askTargetPath();
        int key = askKey();

        List<String> result = new ArrayList<>();
        for (String line : fileManager.readFile(source)) {
            result.add(cipher.decrypt(line, key));
        }
        fileManager.writeFile(result, source, target);
        System.out.println("Готово. Результат записан в " + target);
    }

    private void bruteForceFile() throws CipherException {
        String source = askSourcePath();
        String target = askTargetPath();

        String text = String.join(" ", fileManager.readFile(source));
        String decrypted = bruteForce.decryptByBruteForce(text, ALPHABET);

        fileManager.writeFile(List.of(decrypted), source, target);
        System.out.println("Готово. Результат записан в " + target);
    }

    private String askSourcePath() throws CipherException {
        String path = ask("Введите путь к исходному файлу: ");
        validator.isFileExists(path);
        return path;
    }

    private String askTargetPath() throws CipherException {
        String path = ask("Введите путь к файлу для результата: ");
        if (path.isEmpty()) {
            throw new CipherException("Путь к файлу результата не указан");
        }
        return path;
    }

    private int askKey() throws CipherException {
        String input = ask("Введите ключ (0 - " + (ALPHABET.length - 1) + "): ");
        int key;
        try {
            key = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CipherException("Ключ должен быть целым числом", e);
        }
        validator.isValidKey(key, ALPHABET);
        return key;
    }

    private String ask(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
