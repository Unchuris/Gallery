package unchuris.vladislav.gallery.api.yandex;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.model.ImageYandexDisk;

import static org.junit.Assert.*;

public class ParsingResponseTest {

    private IParsing<ImageYandexDisk> parsing;

    /**
     * DateTimeFormatter based on the ISO8601 standard.
     */
    private DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.dateTimeNoMillis();

    @Before
    public void setUp() {
        parsing = new ParsingResponse();
    }

    @Test
    public void testGetImageListParser() throws JSONException {
        String image1 = "\"name\":\"0_9aad0_6e8d1a17_orig.jpg\"," +
                        "\"created\":\"2018-04-24T16:43:44+00:00\"," +
                        "\"revision\":1524591312028745," +
                        "\"modified\":\"2018-04-24T17:34:54+00:00\"," +
                        "\"preview\":\"https://downloader.disk.yandex.ru/preview/51396a64ca258c0d9392f58bbf57f08e1e911d902138cf533a297e91a9ee4be4/5ae235d7/E9VDlDt5iWKGSyiOUWHkj7Ck_6YzGqwF6KJlFQTY8-1vu2lR3gmkf5yEHZiFtRpXq_J6bpmRyOJonT3VoXnDag%3D%3D?uid=0&filename=0_9aad0_6e8d1a17_orig.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=S&crop=0\"," +
                        "\"media_type\":\"image\"," +
                        "\"path\":\"/0_9aad0_6e8d1a17_orig.jpg\"," +
                        "\"type\":\"file\"," +
                        "\"mime_type\":\"image/jpeg\"," +
                        "\"size\":803620";

        String image2 = "\"name\":\"12-4.jpg\"," +
                        "\"created\":\"2018-04-24T16:43:47+00:00\"," +
                        "\"revision\":1524591312094998," +
                        "\"modified\":\"2018-04-24T17:34:54+00:00\"," +
                        "\"preview\":\"https://downloader.disk.yandex.ru/preview/aa4cb096ff57609beeb797c3457c577e667c18daf73cad945faf13bdc4fcc420/5ae235d7/DSCqeSYAI52SGLkOwSbFwVKpHRbVEZxktTNroTRhXS4uItdf8HoQue85ZSld4wsPrXyN1-hUXPoNM7oZSRFBwg%3D%3D?uid=0&filename=12-4.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=S&crop=0\"," +
                        "\"media_type\":\"image\"," +
                        "\"type\":\"file\"," +
                        "\"mime_type\":\"image/jpeg\"," +
                        "\"size\":266945";

        String response = "{" +
                "\"public_key\":\"Zpy5CgIvS90V57fnro0rqrLLd9WrieTpZUA6ZV1JAWQ=\"," +
                "\"_embedded\":{" +
                    "\"sort\":\"\"," +
                    "\"public_key\":\"Zpy5CgIvS90V57fnro0rqrLLd9WrieTpZUA6ZV1JAWQ=\"," +
                    "\"items\":[" +
                        "{" +
                            image1 +
                        "}," +
                        "{" +
                            image2 +
                        "}" +
                    "]," +
                    "\"limit\":2," +
                    "\"offset\":0," +
                    "\"path\":\"/\"," +
                    "\"total\":2" +
                "}" +
                "}";
        ArrayList<ImageYandexDisk> imageList = parsing.imageListParser(new JSONObject(response));


        ImageYandexDisk iyd1 = new ImageYandexDisk();
        iyd1.setName("0_9aad0_6e8d1a17_orig.jpg");
        iyd1.setPreview("https://downloader.disk.yandex.ru/preview/51396a64ca258c0d9392f58bbf57f08e1e911d902138cf533a297e91a9ee4be4/5ae235d7/E9VDlDt5iWKGSyiOUWHkj7Ck_6YzGqwF6KJlFQTY8-1vu2lR3gmkf5yEHZiFtRpXq_J6bpmRyOJonT3VoXnDag%3D%3D?uid=0&filename=0_9aad0_6e8d1a17_orig.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=S&crop=0");
        iyd1.setSize(803620);
        iyd1.setDateTimeCreated(
                DATE_FORMAT.parseDateTime("2018-04-24T16:43:44+00:00"));
        iyd1.setDateTimeModified(
                DATE_FORMAT.parseDateTime("2018-04-24T17:34:54+00:00"));

        ImageYandexDisk iyd2 = new ImageYandexDisk();
        iyd2.setName("12-4.jpg");
        iyd2.setPreview("https://downloader.disk.yandex.ru/preview/aa4cb096ff57609beeb797c3457c577e667c18daf73cad945faf13bdc4fcc420/5ae235d7/DSCqeSYAI52SGLkOwSbFwVKpHRbVEZxktTNroTRhXS4uItdf8HoQue85ZSld4wsPrXyN1-hUXPoNM7oZSRFBwg%3D%3D?uid=0&filename=12-4.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=S&crop=0");
        iyd2.setSize(266945);
        iyd2.setDateTimeCreated(
                DATE_FORMAT.parseDateTime("2018-04-24T16:43:47+00:00"));
        iyd2.setDateTimeModified(
                DATE_FORMAT.parseDateTime("2018-04-24T17:34:54+00:00"));

        assertEquals(imageList.size(), 2);
        assertEquals(imageList.get(0), iyd1);
        assertEquals(imageList.get(1), iyd2);
    }

    @Test
    public void testGetDownloadUrlParse() throws JSONException {
        JSONObject json = new JSONObject("{" +
                    "\"href\": \"https://downloader.disk.yandex.ru/zip/\"," +
                    "\"method\":\"GET\"," +
                    "\"templated\":false" +
                "}");
        String link = parsing.getDownloadUrlParse(json);
        assertEquals(link, "https://downloader.disk.yandex.ru/zip/");
    }

    @Test
    public void testErrorParse() throws JSONException {
        JSONObject json = new JSONObject("{" +
                "\"message\":\"Ошибка сервера.\"," +
                "\"description\":\"Internal Server Error\"," +
                "\"error\":\"InternalServerError\"" +
                "}");
        assertEquals(parsing.errorParse(json), "InternalServerError: Internal Server Error");
    }

    @Test(expected = JSONException.class)
    public void testnNoFiled() throws JSONException {
        String image2 = "\"name1\":\"12-4.jpg\"," +
                "\"created\":\"2018-04-24T16:43:47+00:00\"," +
                "\"revision\":1524591312094998," +
                "\"modified\":\"2018-04-24T17:34:54+00:00\"," +
                "\"preview\":\"https://downloader.disk.yandex.ru/preview/aa4cb096ff57609beeb797c3457c577e667c18daf73cad945faf13bdc4fcc420/5ae235d7/DSCqeSYAI52SGLkOwSbFwVKpHRbVEZxktTNroTRhXS4uItdf8HoQue85ZSld4wsPrXyN1-hUXPoNM7oZSRFBwg%3D%3D?uid=0&filename=12-4.jpg&disposition=inline&hash=&limit=0&content_type=image%2Fjpeg&tknv=v2&size=S&crop=0\"," +
                "\"media_type\":\"image\"," +
                "\"type\":\"file\"," +
                "\"mime_type\":\"image/jpeg\"," +
                "\"size\":266945";
        parsing.imageParse(new JSONObject(image2));
    }
}
