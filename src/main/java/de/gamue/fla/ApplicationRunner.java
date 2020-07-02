package de.gamue.fla;

import de.gamue.fla.output.ConsoleWriter;
import de.gamue.fla.output.CsvWriter;
import de.gamue.fla.output.OutputWriter;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationRunner {

    public static void main(String[] args) {
        log.info("Application started.");

        CliConfig config = CliConfig.read(args);

        String directory = config.getDirectory();
        if (directory != null) {
            FocalLengthAnalyzer focalLengthAnalyzer = new FocalLengthAnalyzer();
            Map<Float, Integer> focalLengthToAmount = focalLengthAnalyzer.getFocalLengthUsage(directory);

            OutputWriter output;
            if (config.getOutputFile().isBlank()) {
                output = new ConsoleWriter();
            } else {
                output = new CsvWriter(config.getOutputFile());
            }
            output.write(focalLengthToAmount);
        }
        log.info("Application ended.");
    }

}
