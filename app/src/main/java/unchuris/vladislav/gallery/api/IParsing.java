package unchuris.vladislav.gallery.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface for parsing json object format.
 * @param <T> model.
 */
public interface IParsing<T> {

    ArrayList<T> imageListParser(JSONObject response) throws JSONException;

    T imageParse(JSONObject response) throws JSONException;

    String getDownloadUrlParse(JSONObject response) throws JSONException;

    String errorParse(JSONObject response) throws JSONException;
}
