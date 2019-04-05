package de.gamue;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExifReaderTest {

    private ExifReader reader;

    @BeforeEach
    void setup() {
        reader = new ExifReader();
    }

    @Test
    void getFocalLength() throws ImageProcessingException, IOException, MetadataException {
        float focalLength = reader.getFocalLength(getTestImage());
        Assertions.assertEquals(46, focalLength);
    }

    @Test
    void getFocalLength_FileNotFound() {
        File image = new File("NotExisting");
        Assertions.assertThrows(FileNotFoundException.class, () -> reader.getFocalLength(image));
    }

    private File getTestImage() {
        return new File(getClass().getClassLoader().getResource("iceland_01.jpg").getFile());
    }
}