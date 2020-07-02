package de.gamue.fla.output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvWriter implements OutputWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvWriter.class);

    private String outputFile;

    public CsvWriter(String outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void write(Map<Float, Integer> focalLengthToAmount) {
        StringBuilder stringBuilder = new StringBuilder(focalLengthToAmount.size() * 10);
        stringBuilder.append("Focal length").append(";").append("Count").append('\n');
        focalLengthToAmount.entrySet().stream()
                .forEach(e -> stringBuilder.append(e.getKey()).append(";").append(e.getValue()).append('\n'));

        Path path = Paths.get(outputFile);
        try {
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            LOGGER.error("Error while writing result to file.", e);
        }

        LOGGER.info("Analysis result written to {}", path);
    }
}
