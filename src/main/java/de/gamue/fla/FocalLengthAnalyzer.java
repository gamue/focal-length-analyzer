package de.gamue.fla;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FocalLengthAnalyzer {

    /**
     * Analyzes the image files in the given directory, including all subdirectories, and return information how often a
     * certain focal length has been used.
     *
     * @param directory path to the directory that should be checked
     * @return map that holds the focal length as key and usage count as value
     */
    public Map<Float, Integer> getFocalLengthUsage(String directory) {
        ExifReader exifReader = new ExifReader();
        Map<Float, Integer> focalLengthToAmount = new TreeMap<>();

        List<Path> files = getFilesToCheck(directory);
        log.info("found {} files in directories.", files.size());

        files.forEach(
                file -> {
                    try {
                        float focalLength = exifReader.getFocalLength(file.toFile());
                        Integer amount = focalLengthToAmount.getOrDefault(focalLength, 0);
                        amount++;
                        focalLengthToAmount.put(focalLength, amount);
                    } catch (ImageProcessingException | IOException | MetadataException e) {
                        // no image file or no exif data present
                        log.debug("could not read exif data from file: " + file.toString(), e);
                    } catch (Exception e) {
                        log.error("error while reading file: " + file.toString(), e);
                    }
                });

        return focalLengthToAmount;
    }

    private List<Path> getFilesToCheck(String directory) {
        try {
            return Files.walk(Paths.get(directory)).filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error while resolving files to check.");
            return Collections.emptyList();
        }
    }
}
