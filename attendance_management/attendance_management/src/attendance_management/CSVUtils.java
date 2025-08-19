package attendance_management;

import java.io.*;
import java.util.*;

public class CSVUtils {
    public static List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void writeCSV(String filePath, List<String[]> data) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                pw.println(String.join(",", row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendCSV(String filePath, String[] row) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.println(String.join(",", row));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void removeCSV(String filePath, String match) {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                // if the line contains the match string, skip it
                if (!line.contains(match)) {
                    writer.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Replace original file with the temp file
        if (!inputFile.delete()) {
            System.out.println("Could not delete original file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file");
        }
    }

}
