package unchuris.vladislav.gallery.utils;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.app.AppController;

/**
 * Fetcher.
 * @param <T> model.
 */
public class Fetcher<T> {
    /**
     * Log tag.
     */
    private final String TAG = Fetcher.class.getSimpleName();
    /**
     * Instance the callback.
     */
    private IResponseCallback<T> callback;

    /**
     * Constructor.
     * @param callback callback.
     */
    public Fetcher(IResponseCallback<T> callback){
        this.callback = callback;
    }

    /**
     * Fetch images.
     * @param pathToDownload path for querying.
     * @param parsing parse the response as json.
     */
    public void fetchImages(final String pathToDownload, final IParsing<T> parsing) {
        StringRequest req = new StringRequest(pathToDownload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            callback.response(parsing.imageListParser(new JSONObject(response)));
                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }
}
