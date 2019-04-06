package de.gamue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;

public class ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunner.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");

        CliConfig config = CliConfig.read(args);

        String directory = config.getDirectory();
        if (directory != null) {
            FocalLengthAnalyzer focalLengthAnalyzer = new FocalLengthAnalyzer();
            Map<Float, Integer> focalLengthToAmount = focalLengthAnalyzer.getFocalLengthUsage(directory);

            for (Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
                System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
            }
        }
        LOGGER.info("Application ended.");
    }

}
