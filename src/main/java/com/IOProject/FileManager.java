package com.IOProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class FileManager {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);
        System.out.println("File Manager");

        // directoryPath - C:\Users\admin\Documents\WIN\CTAC_5\FullStack103\Day 59\IO
        System.out.println("Please provide the directory:");
        String inputPath = scanner.nextLine();

        Path directoryPath = Paths.get(inputPath);
        if (!Files.isDirectory(directoryPath)) {
            System.out.println("Invalid directory path.");
            return;
        }


        // Menu of choices
        boolean exit = false;
        while (!exit) {
            System.out.println("Menu: ");
            System.out.println("1. Display contents of directory");
            System.out.println("2. Copy file");
            System.out.println("3. Move file");
            System.out.println("4. Delete file");
            System.out.println("5. Create directory");
            System.out.println("6. Delete directory");
            System.out.println("7. Search for file");
            System.out.println("8. I'm finished");
            int action = scanner.nextInt();
            scanner.nextLine();

            // do what we choose
            switch (action) {
                case 1:
                    displayDirectory(directoryPath);
                    break;
                case 2:
                    copyFile(directoryPath, scanner);
                    break;
                case 3:
                    moveFile(directoryPath, scanner);
                    break;
                case 4:
                    deleteFile(directoryPath, scanner);
                    break;
                case 5:
                    createDirectory(directoryPath, scanner);
                    break;
                case 6:
                    deleteDirectory(directoryPath, scanner);
                    break;
                case 7:
                    searchFiles(directoryPath, scanner);
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Error: Invalid!");
            }
        }
    }

    //display contents of directory
    private static void displayDirectory(Path directoryPath) {
        try {
            Files.walk(directoryPath)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        File file = path.toFile();
                        System.out.println(file.getName() + " " + file.length() + " " + getLastModifiedFile(file));
                    });
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // format the last modified times
    private static String getLastModifiedFile(File file) {
        Path filePath = file.toPath();
        try {
            FileTime lastModifiedTime = Files.getLastModifiedTime(filePath);
            LocalDateTime dateTime = LocalDateTime.ofInstant(lastModifiedTime.toInstant(), ZoneOffset.systemDefault());
            return dateTime.format(formatter);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }

    //copy file
        private static void copyFile(Path directoryPath, Scanner scanner) {
            System.out.println("Enter the source file name:");
            String sourceFile = scanner.nextLine();
            System.out.println("Enter the target file name:");
            String targetFile = scanner.nextLine();
            try {
                Path sourcePath = directoryPath.resolve(sourceFile);
                Path targetPath = directoryPath.resolve(targetFile);
                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // move file
        private static void moveFile(Path directoryPath, Scanner scanner) {
            System.out.println("Enter the source file name:");
            String sourceFile = scanner.nextLine();
            System.out.println("Enter the target file name:");
            String targetFile = scanner.nextLine();
            try {
                Path sourcePath = directoryPath.resolve(sourceFile);
                Path targetPath = directoryPath.resolve(targetFile);
                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File moved!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        //delete file
        private static void deleteFile(Path directoryPath, Scanner scanner) {
            System.out.println("Enter the file name to delete:");
            String fileToDelete = scanner.nextLine();
            try {
                Path filePath = directoryPath.resolve(fileToDelete);
                Files.delete(filePath);
                System.out.println("File deleted!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // make new directory
        private static void createDirectory(Path directoryPath, Scanner scanner) {
            System.out.println("What do you want to name your new directory? ");
            String newDirectoryName = scanner.nextLine();
            try {
                Path newDirectoryPath = directoryPath.resolve(newDirectoryName);
                Files.createDirectory(newDirectoryPath);
                System.out.println("Directory created!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // delete a directory
        private static void deleteDirectory(Path directoryPath, Scanner scanner) {
            System.out.println("Enter the directory name to delete:");
            String directoryToDelete = scanner.nextLine();
            try {
                Path directoryToDeletePath = directoryPath.resolve(directoryToDelete);
                Files.walk(directoryToDeletePath)
                        .map(Path::toFile)
                        .forEach(File::delete);
                System.out.println("Directory deleted!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        // search the files
        private static void searchFiles(Path directoryPath, Scanner scanner) {
            System.out.println("What file are you searching for?");
            String searchFileName = scanner.nextLine();
            try {
                Files.walk(directoryPath)
                        .filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().contains(searchFileName))
                        .forEach(System.out::println);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
}