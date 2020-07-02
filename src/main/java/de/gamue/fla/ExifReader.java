package de.gamue.fla;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class ExifReader {

    public float getFocalLength(File image) throws ImageProcessingException, IOException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(image);
        Collection<ExifSubIFDDirectory> directories = metadata.getDirectoriesOfType(ExifSubIFDDirectory.class);
        if (!directories.isEmpty()) {
            for (ExifSubIFDDirectory directory : directories) {
                if (directory.containsTag(ExifSubIFDDirectory.TAG_FOCAL_LENGTH)) {
                    return directory.getFloat(ExifSubIFDDirectory.TAG_FOCAL_LENGTH);
                }
            }
            throw new MetadataException("Focal Length not set in exif data.");
        } else {
            throw new MetadataException("No ExifSubIFDDirectory present.");
        }
    }

    public String getCameraName(File image) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(image);
        Collection<ExifIFD0Directory> directories = metadata.getDirectoriesOfType(ExifIFD0Directory.class);
        if (!directories.isEmpty()) {
            for (ExifIFD0Directory directory : directories) {
                if (directory.containsTag(ExifIFD0Directory.TAG_MODEL)) {
                    return directory.getString(ExifIFD0Directory.TAG_MODEL);
                }
            }
        }
        return null;
    }

}
