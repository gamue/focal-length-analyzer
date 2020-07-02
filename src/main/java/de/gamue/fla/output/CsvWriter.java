package de.gamue.fla.output;

import de.gamue.fla.FocalLengthResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CsvWriter implements OutputWriter {

    private static final String DELIMITER = ";";
    private static final char LINEBREAK = '\n';
    private String outputFile;

    @Override
    public void write(Collection<FocalLengthResult> results) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Focal length")
                .append(DELIMITER)
                .append("Count")
                .append(DELIMITER)
                .append("Camera")
                .append(LINEBREAK);

        results.forEach(result -> {
                    Map<Float, Integer> focalLengthToAmount = result.getFocalLengthToAmount();
                    focalLengthToAmount.entrySet().stream()
                            .forEach(e -> stringBuilder.append(e.getKey())
                                    .append(DELIMITER)
                                    .append(e.getValue())
                                    .append(DELIMITER)
                                    .append(result.getCameraName())
                                    .append(LINEBREAK));
                }
        );

        Path path = Paths.get(outputFile);
        try {
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            log.error("Error while writing result to file.", e);
        }
        log.info("Analysis result written to {}", path);
    }
}
