package de.gamue.fla;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FocalLengthAnalyzer {

    private static final String UNKNOWN_CAMERA_NAME = "unknown";
    private static final String ALL_CAMERAS = "all";

    /**
     * Analyzes the image files in the given directory, including all subdirectories, and return information how often a
     * certain focal length has been used.
     *
     * @param directory     path to the directory that should be checked
     * @param splitByCamera if {@code true} the result will be split by camera used
     * @return collection of analysis result holding the used focal lengths and their amount.
     * If the result is not split by camera the list has only one item.
     */
    public Collection<FocalLengthResult> getFocalLengthUsage(String directory, boolean splitByCamera) {
        ExifReader exifReader = new ExifReader();
        Map<String, FocalLengthResult> cameraToResult = new HashMap<>();

        List<Path> files = getFilesToCheck(directory);
        log.info("found {} files in directories.", files.size());

        files.forEach(
                file -> {
                    try {
                        File image = file.toFile();
                        String cameraName = getCameraName(exifReader, image, splitByCamera);
                        float focalLength = exifReader.getFocalLength(image);

                        FocalLengthResult result = cameraToResult.getOrDefault(cameraName, new FocalLengthResult(cameraName));
                        result.increaseAmount(focalLength);
                        cameraToResult.put(cameraName, result);

                    } catch (ImageProcessingException | IOException | MetadataException e) {
                        // no image file or no exif data present
                        log.debug("could not read exif data from file: " + file.toString(), e);
                    } catch (Exception e) {
                        log.error("error while reading file: " + file.toString(), e);
                    }
                });

        return cameraToResult.values();
    }

    private String getCameraName(ExifReader exifReader, File image, boolean splitByCamera) throws ImageProcessingException, IOException {
        if (splitByCamera) {
            String cameraName = exifReader.getCameraName(image);
            if (cameraName == null) {
                return UNKNOWN_CAMERA_NAME;
            }
            return cameraName;
        }
        return ALL_CAMERAS;
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
