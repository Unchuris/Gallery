package unchuris.vladislav.gallery.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.app.GalleryApplication;

public class Fetcher<T> {
    /**
     * Log tag.
     */
    private static final String TAG = Fetcher.class.getSimpleName();

    private IResponseCallback<T> callback;

    private ProgressDialog pDialog;

    private Context context;

    public Fetcher(final Context context, final IResponseCallback<T> callback) {
        this.callback = callback;
        pDialog = new ProgressDialog(context);
        this.context = context;
    }

    public void fetchImages(final String pathToDownload, final IParsing<T> parsing) {
        pDialog.setMessage("Downloading json...");
        pDialog.show();
        StringRequest req = new StringRequest(pathToDownload,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        pDialog.hide();
                        try {
                            callback.response(parsing.imageListParser(new JSONObject(response)));
                        } catch (JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            callback.errorMessage(context.getString(R.string.error) +
                                    "Json parsing error: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                callback.errorMessage(context.getString(R.string.error)
                        + "Error:" + error.getMessage());
                pDialog.hide();
            }
        });
        GalleryApplication.getInstance().addToRequestQueue(req);
    }
}
