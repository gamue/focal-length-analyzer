package de.gamue;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import java.io.File;
import java.io.IOException;

public class ExifReader {

    public float getFocalLength(File image) throws ImageProcessingException, IOException, MetadataException {
        Metadata metadata = ImageMetadataReader.readMetadata(image);
        ExifSubIFDDirectory exifDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifDirectory == null) {
            throw new MetadataException("no SubIFDirectory");
        }
        return exifDirectory.getFloat(ExifSubIFDDirectory.TAG_FOCAL_LENGTH);
    }

}
