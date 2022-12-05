package com.example.demo1.writer;

import com.example.demo1.entity.MyItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MyFileWriter {
    private static final File csvFile = new File("items.csv");

    public static void writeToFile(List<MyItem> items) throws FileNotFoundException {
        try (PrintWriter printWriter = new PrintWriter(csvFile);) {
            for (MyItem item : items) {
                printWriter.println(item.getId() + " " + item.getName() + " " + item.getYearOfRelease());
            }
        }
    }

    public static List<String> readToFile() throws IOException {
        List<String> items = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvFile);
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) break;
                items.add(line);
            }
        }
        return items;
    }
}
