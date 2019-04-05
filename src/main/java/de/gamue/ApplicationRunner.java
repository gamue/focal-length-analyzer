package de.gamue;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRunner.class);

    private static final String PATH = "/Users/jganzenmller/Downloads";

    public static void main(String[] args) throws IOException {

        ExifReader exifReader = new ExifReader();
        Map<Float, Integer> focalLengthToAmount = new TreeMap<>();

        Stream<Path> files = Files.walk(Paths.get(PATH));
        files.filter(Files::isRegularFile)
            .forEach(
                file -> {
                    try {
                        float focalLength = exifReader.getFocalLength(file.toFile());
                        Integer amount = focalLengthToAmount.getOrDefault(focalLength, 0);
                        amount++;
                        focalLengthToAmount.put(focalLength, amount);
                    } catch (ImageProcessingException | IOException | MetadataException e) {
                        // no image file or no exif data present
                        LOGGER.debug("could not read exif data from file: " + file.toString(), e);
                    }
                });
        files.close();

        for (Entry<Float, Integer> lengthToAmount : focalLengthToAmount.entrySet()) {
            System.out.println(lengthToAmount.getKey() + "mm - " + lengthToAmount.getValue());
        }
    }

}
