package de.gamue.fla;

import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExifReaderTest {

    private ExifReader reader;

    @BeforeEach
    void setup() {
        reader = new ExifReader();
    }

    @Test
    void getFocalLength() throws ImageProcessingException, IOException, MetadataException {
        assertEquals(46, reader.getFocalLength(getTestImageDslr()));
        assertEquals(4.15f, reader.getFocalLength(getTestImagePhone()));
    }

    @Test
    void getFocalLength_FileNotFound() {
        File image = new File("NotExisting");
        Assertions.assertThrows(FileNotFoundException.class, () -> reader.getFocalLength(image));
    }

    @Test
    void getFocalLength_NoExifData() {
        File image = getTestImageBlank();
        Assertions.assertThrows(MetadataException.class, () -> reader.getFocalLength(image));
    }

    @Test
    void getFocalLength35mm() throws ImageProcessingException, IOException, MetadataException {
        assertEquals(69, reader.getFocalLength35mmEquivalent(getTestImageDslr()));
        assertEquals(52, reader.getFocalLength35mmEquivalent(getTestImagePhone()));
    }

    @Test
    void getCameraName() throws ImageProcessingException, IOException, MetadataException {
        assertEquals("NIKON D7000", reader.getCameraName(getTestImageDslr()));
        assertEquals("iPhone 6", reader.getCameraName(getTestImagePhone()));
        Assertions.assertNull(reader.getCameraName(getTestImageBlank()));
    }

    private File getTestImageDslr() {
        return new File(getClass().getClassLoader().getResource("iceland_01.jpg").getFile());
    }

    private File getTestImagePhone() {
        return new File(getClass().getClassLoader().getResource("jordan_211.jpg").getFile());
    }

    private File getTestImageBlank() {
        return new File(getClass().getClassLoader().getResource("blank.jpg").getFile());
    }
}