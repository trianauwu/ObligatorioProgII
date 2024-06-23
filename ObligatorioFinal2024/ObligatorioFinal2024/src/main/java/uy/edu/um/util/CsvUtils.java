package uy.edu.um.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvUtils {

    public static MyLinkedListImpl<String[]> parseCsvFile(String filePath) {
        MyLinkedListImpl<String[]> records = new MyLinkedListImpl<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parsedLine = parseCsvLine(line);
                records.add(parsedLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

    private static String[] parseCsvLine(String line) {
        return line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    }
}

