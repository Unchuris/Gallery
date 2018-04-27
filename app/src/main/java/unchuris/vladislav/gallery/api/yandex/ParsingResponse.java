package unchuris.vladislav.gallery.api.yandex;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.model.ImageYandexDisk;

/**
 * Parsing response from Yandex disk api.
 */
public class ParsingResponse implements IParsing<ImageYandexDisk> {
    /**
     * DateTimeFormatter based on the ISO8601 standard.
     */
    private DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.dateTimeNoMillis();

    /**
     *
     * @param response response in json object format.
     * @return ArrayList<ImageYandexDisk> for images.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    public ArrayList<ImageYandexDisk> imageListParser(final JSONObject response) throws JSONException {
        ArrayList<ImageYandexDisk> imageList = new ArrayList<>();
        JSONObject embedded = response.getJSONObject("_embedded");
        JSONArray returnedData = embedded.getJSONArray("items");
        for (int i = 0; i < returnedData.length(); i++) {
            JSONObject object = returnedData.getJSONObject(i);
            imageList.add(imageParse(object));
        }
        return imageList;
    }

    /**
     *
     * @param response response in json object format.
     * @return ImageYandexDisk model.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    public ImageYandexDisk imageParse(final JSONObject response) throws JSONException {
        ImageYandexDisk image = new ImageYandexDisk();
        image.setName(response.getString("name"));
        image.setPreview(response.getString("preview"));
        image.setSize(response.getInt("size"));
        image.setDateTimeCreated(
                DATE_FORMAT.parseDateTime(response.getString("created")));
        image.setDateTimeModified(
                DATE_FORMAT.parseDateTime(response.getString("modified")));
        return image;
    }

    /**
     *
     * @param response response in json object format.
     * @return url to download.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    public String getDownloadUrlParse(final JSONObject response) throws JSONException {
        return response.getString("href");
    }

    /**
     *
     * @param response response in json object format.
     * @return error message.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    public String errorParse(final JSONObject response) throws JSONException {
        return response.getString("error") + ": "
                + response.getString("description");
    }
}
