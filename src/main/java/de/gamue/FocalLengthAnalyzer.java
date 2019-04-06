package de.gamue;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FocalLengthAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FocalLengthAnalyzer.class);

    /**
     * Analyzes the image files in the given directory, including all subdirectories, and return information how often a
     * certain focal length has been used.
     *
     * @param directory path to the directory that should be checked
     * @return map that holds the focal length as key and usage count as value
     */
    public Map<Float, Integer> getFocalLengthUsage(String directory) throws IOException {
        ExifReader exifReader = new ExifReader();
        Map<Float, Integer> focalLengthToAmount = new TreeMap<>();

        Stream<Path> files = Files.walk(Paths.get(directory));
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

        return focalLengthToAmount;
    }

}