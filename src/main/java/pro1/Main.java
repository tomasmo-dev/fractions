package pro1;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        Path rootDir = Paths.get(System.getProperty("user.dir"));
        Path inputDir = Paths.get(rootDir.toString(), "input");
        Path outputDir = Paths.get(rootDir.toString(), "output");

        folderExists(inputDir);
        folderExists(outputDir);

        List<Path> fs = Files
                .list(inputDir)
                .toList();

        if (fs.isEmpty())
        {
            System.err.println("Input directory is empty: " + inputDir);
            System.exit(1); // Exit with 1
        }

        var csvs = fs.stream()
                .filter(f -> f.toString().endsWith(".csv"))
                .collect(Collectors.toList());

        var zips = fs.stream()
                .filter(f -> f.toString().endsWith(".zip"))
                .collect(Collectors.toList());

        if (csvs.isEmpty() && zips.isEmpty())
        {
            System.err.println("Input directory does not contain any .csv or .zip files: " + inputDir);
            System.exit(1); // Exit with 1
        }

        // Step 1. process all csv files
        CsvProcessing.processCsvFiles(csvs);
        // Step 2. process all zip files
        ZipProcessing.processZipFiles(zips, outputDir);

    }

    /// Check if a folder exists. if not print an error and exit the program
    private static void folderExists(Path folder)
    {
        if (!(Files.exists(folder) && Files.isDirectory(folder))) {
            System.err.println("Input directory does not exist: " + folder);
            System.exit(1); // Exit with 1
        }
    }

    private static void LogInfo(String message)
    {
        System.out.println("[Info] " + message);
    }

    private static <T> void printList(List<T> list)
    {
        for (var o : list) {
            LogInfo(o.toString());
        }
    }

}
