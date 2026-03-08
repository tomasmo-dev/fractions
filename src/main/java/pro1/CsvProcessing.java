package pro1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvProcessing {

        /// process a list of csvs and create a list of completed csvs in the output folder
        public static void processCsvFiles(List<Path> csvs) {
            for (Path csv : csvs) {
                processCsvFile(csv);
            }
        }

        private static void processCsvFile(Path csv) {
            List<String> csvLines;

            try {
                csvLines = Files.readAllLines(csv, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < csvLines.size(); i++) {
                var processedLine = Shared.ProcessLine(csvLines.get(i));

                csvLines.set(i, processedLine);
            }

            Path outputPath = Path.of("output", csv.getFileName().toString());
            try {
                Files.write(outputPath, csvLines, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
