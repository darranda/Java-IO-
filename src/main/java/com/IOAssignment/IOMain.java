package com.IOAssignment;

import java.io.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class IOMain {
    public static void main(String[] args) {

        try {
            List<Integer> input1 = readFile("C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\Day 59\\IO\\src\\main\\java\\com\\IOAssignment\\input1.txt");
            List<Integer> input2 = readFile("C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\Day 59\\IO\\src\\main\\java\\com\\IOAssignment\\input2.txt");

            // merge contents of files
            List<Integer> mergedList = mergeFiles(input1, input2);

            // write merged list to a file
            writeToFile(mergedList, "C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\Day 59\\IO\\src\\main\\java\\com\\IOAssignment\\merge.txt");

            // common list to a file
            List<Integer> commonList = commonInts(input1, input2);
            writeToFile(commonList, "C:\\Users\\admin\\Documents\\WIN\\CTAC_5\\FullStack103\\Day 59\\IO\\src\\main\\java\\com\\IOAssignment\\common.txt");

            System.out.println("Files merged successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

    }

    private static List<Integer> readFile(String filename) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line);
                numbers.add(number);
            }
        }
        return numbers;
    }

    private static List<Integer> mergeFiles(List<Integer> input1, List<Integer> input2) {
        List<Integer> mergedList = new ArrayList<>(input1);
        mergedList.addAll(input2);
        return mergedList;
    }

    private static List<Integer> commonInts(List<Integer> input1, List<Integer> input2) {
        List<Integer> commonList = new ArrayList<>();
        for (int number : input1) {
            if (input2.contains(number)) {
                commonList.add(number);
            }
        }
        return commonList;
    }

    private static void writeToFile(List<Integer> numbers, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int number : numbers) {
                writer.write(String.valueOf(number));
                writer.newLine();
            }
        }
    }
}
