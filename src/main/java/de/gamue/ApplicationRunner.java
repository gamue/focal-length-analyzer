package de.gamue;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class ApplicationRunner {

    public static void main(String[] args) throws IOException {

        CliConfig config = CliConfig.read(args);

        String directory = config.getDirectory();
        if (directory != null) {
            FocalLengthAnalyzer focalLengthAnalyzer = new FocalLengthAnalyzer();
            Map<Float, Integer> focalLengthToAmount = focalLengthAnalyzer.getFocalLengthUsage(directory);

            for (Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
                System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
            }
        }
    }

}
