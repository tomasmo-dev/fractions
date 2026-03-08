package pro1;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipProcessing {
        /// process a list of zip files and create a list of completed csvs in the output folder
        public static void processZipFiles(List<Path> zips, Path outputDir) {
            for (Path path : zips) {
                try{
                    processZipFile(path, outputDir);
                }
                catch (IOException e){
                    System.err.println("Error processing zip file: " + path);
                    e.printStackTrace();
                }
            }
        }

        private static void processZipFile(Path zip, Path outputDir) throws IOException {
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(zip.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ZipOutputStream zos = null;
            try {
                var outputZipPath = outputDir.resolve(zip.getFileName().toString());
                zos = new ZipOutputStream(new FileOutputStream(outputZipPath.toFile()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();

                if (!entry.isDirectory() && entry.getName().endsWith(".csv")) {
                    var iStream = zipFile.getInputStream(entry);
                    var lines = new BufferedReader(
                            new InputStreamReader(iStream, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.toList());

                    for (int i = 0; i < lines.size(); i++) {

                        var processedLine = Shared.ProcessLine(lines.get(i));

                        lines.set(i, processedLine);
                    }

                    zos.putNextEntry(entry);
                    zos.write(String.join("\n", lines).getBytes(StandardCharsets.UTF_8));
                    zos.closeEntry();
                }
            }

            zipFile.close();
            zos.close();
        }
}
