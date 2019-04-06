package de.gamue.fla;

import de.gamue.fla.output.ConsoleWriter;
import de.gamue.fla.output.CsvWriter;
import de.gamue.fla.output.OutputWriter;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");

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
        LOGGER.info("Application ended.");
    }

}
