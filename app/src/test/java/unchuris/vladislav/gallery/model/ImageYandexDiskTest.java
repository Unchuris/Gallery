package unchuris.vladislav.gallery.model;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ImageYandexDiskTest {

    /**
     * GenerationLink.
     */
    private ImageYandexDisk image;

    /**
     * DateTimeFormatter based on the ISO8601 standard.
     */
    private DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.dateTimeNoMillis();

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

    @Test
    public void testModel() {
        ImageYandexDisk imageYandexDisk = new ImageYandexDisk();
        imageYandexDisk.setPreview("1");
        imageYandexDisk.setDateTimeModified(DATE_FORMAT.parseDateTime("2018-04-24T16:43:44+00:00"));
        imageYandexDisk.setDateTimeCreated(DATE_FORMAT.parseDateTime("2018-04-24T16:43:44+00:00"));
        imageYandexDisk.setSize(1);
        imageYandexDisk.setName("test");
        assertEquals(imageYandexDisk.getPreview(), "1");
        assertEquals(imageYandexDisk.getDateTimeModified(), DATE_FORMAT.parseDateTime("2018-04-24T16:43:44+00:00"));
        assertEquals(imageYandexDisk.getDateTimeCreated(), DATE_FORMAT.parseDateTime("2018-04-24T16:43:44+00:00"));
        assertEquals(imageYandexDisk.getName(), "test");
        assertTrue(imageYandexDisk.getSize() == 1);
    }
}
