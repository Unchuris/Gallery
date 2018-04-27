package unchuris.vladislav.gallery.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface for parsing json object format.
 * @param <T> model.
 */
public interface IParsing<T> {
    /**
     *
     * @param response response in json object format.
     * @return ArrayList<Model> for images
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    ArrayList<T> imageListParser(JSONObject response) throws JSONException;

    /**
     *
     * @param response response in json object format.
     * @return image model.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    T imageParse(JSONObject response) throws JSONException;

    /**
     *
     * @param response response in json object format.
     * @return url to download.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    String getDownloadUrlParse(JSONObject response) throws JSONException;

    /**
     *
     * @param response response in json object format.
     * @return error message.
     * @throws JSONException indicates that some exception happened during JSON processing.
     */
    String errorParse(JSONObject response) throws JSONException;
}
