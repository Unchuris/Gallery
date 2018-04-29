package unchuris.vladislav.gallery.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class ImageYandexDiskTest {

    /**
     * GenerationLink.
     */
    private ImageYandexDisk image;

    @Before
    public void setUp() {
        image = new ImageYandexDisk();
    }

    @Test
    public void testGetPreview() {
        image.setPreview("http://ex.com?name=123.jpg");
        String rez = image.getPreview("XXL");
        assertEquals("http://ex.com?size=XXL&name=123.jpg", rez);
    }
}
