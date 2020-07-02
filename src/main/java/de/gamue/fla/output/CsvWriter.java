package de.gamue.fla.output;

import de.gamue.fla.FocalLengthResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class CsvWriter implements OutputWriter {

    private String outputFile;

    @Override
    public void write(FocalLengthResult result) {
        Map<Float, Integer> focalLengthToAmount = result.getFocalLengthToAmount();
        StringBuilder stringBuilder = new StringBuilder(focalLengthToAmount.size() * 10);
        stringBuilder.append("Focal length").append(";").append("Count").append('\n');
        focalLengthToAmount.entrySet().stream()
                .forEach(e -> stringBuilder.append(e.getKey()).append(";").append(e.getValue()).append('\n'));

        Path path = Paths.get(outputFile);
        try {
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (IOException e) {
            log.error("Error while writing result to file.", e);
        }

        log.info("Analysis result written to {}", path);
    }
}
