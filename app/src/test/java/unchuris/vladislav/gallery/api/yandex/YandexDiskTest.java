package unchuris.vladislav.gallery.api.yandex;

import org.junit.Before;
import org.junit.Test;

import unchuris.vladislav.gallery.api.IApiGenerationLink;

import static junit.framework.Assert.assertEquals;

public class YandexDiskTest {

    /**
     * GenerationLink.
     */
    private IApiGenerationLink generationLink;


    @Before
    public void setUp() {
        generationLink = new YandexDisk();
    }

    @Test
    public void testGetPublicLink() {
        String publicKey = "https://yadi.sk/d/2juJHwM13UjXGw";
        String queryParameters = "preview_size=M";
        String link = generationLink.getPublicLink(publicKey, queryParameters);
        assertEquals("https://cloud-api.yandex.net/v1/disk/public/resources" +
                "?public_key=https://yadi.sk/d/2juJHwM13UjXGw" +
                "&preview_size=M", link);
    }

    @Test
    public void testGetQueryParameters() {
        String rez = generationLink.getQueryParameters("M", 150, "modified");
        assertEquals("preview_size=M&preview_crop=true&limit=150&sort=modified",rez);
    }

}
