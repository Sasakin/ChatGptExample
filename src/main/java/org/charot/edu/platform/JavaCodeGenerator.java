package org.charot.edu.platform;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JavaCodeGenerator {
    public static void main(String[] args) {
        String text = "public class MyClass {\n" +
                "    private int myField;\n" +
                "    \n" +
                "    public void myMethod() {\n" +
                "        // Код метода\n" +
                "    }\n" +
                "}";

        String filePath = "/path/";

        try {
            parseAndSaveJavaClass(text, filePath);
            System.out.println("Java file generated successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error while generating Java file: " + e.getMessage());
        }
    }

    public static void parseAndSaveJavaClass(String text, String filePath) throws IOException {
        // Парсим Java-класс из строки text
        CompilationUnit compilationUnit = StaticJavaParser.parse(text);

        // Получаем корневой узел CompilationUnit
        Node rootNode = compilationUnit.getChildNodes().get(0);

        // Проверяем, является ли корневой узел классом
        if (rootNode instanceof TypeDeclaration) {
            TypeDeclaration<?> typeDeclaration = (TypeDeclaration<?>) rootNode;

            File file = new File("src/main/java" + filePath + typeDeclaration.getNameAsString() +".java");

            String directoryName = "src/main/java" + filePath;

            // Получаем текущую рабочую директорию
            String currentDirectory = System.getProperty("user.dir");

            // Создаем объект Path для новой директории
            Path directoryPath = Paths.get(currentDirectory, directoryName);
            // Создаем директорию
            Files.createDirectory(directoryPath);
            System.out.println("Директория успешно создана!");

            // Сохраняем код в файл
            Files.write(Paths.get(file.getPath()), serializeTypeDeclaration(typeDeclaration), StandardOpenOption.CREATE);
        }
    }

        public static byte[] serializeTypeDeclaration(TypeDeclaration typeDeclaration) {
                return typeDeclaration.getTokenRange()
                        .get().toString()
                        .getBytes(StandardCharsets.UTF_8);
        }

}
