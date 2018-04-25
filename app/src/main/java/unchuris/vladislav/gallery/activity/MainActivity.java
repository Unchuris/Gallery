package unchuris.vladislav.gallery.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.api.IApiGenerationLink;
import unchuris.vladislav.gallery.api.yandex.YandexDisk;
import unchuris.vladislav.gallery.app.AppController;
import unchuris.vladislav.gallery.model.ImageYandexDisk;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Log tag.
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Public folder on Yandex disk.
     */
    private static final String PUBLIC_FOLDER_URL = "https://yadi.sk/d/2juJHwM13UjXGw";

    /**
     * Number of columns to display the images.
     */
    private static final Integer SPAN_COUNT = 2;

    /**
     * Array with images.
     */
    private ArrayList<ImageYandexDisk> images;

    /**
     * Full link to the public folder.
     */
    private String pathToDownload = "";

    /**
     * Progress dialog.
     */
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        IApiGenerationLink yd = new YandexDisk();
        pathToDownload = yd.getPublicLink(PUBLIC_FOLDER_URL, "preview_size=M&preview_crop=true");

        fetchImages();
    }

    private void fetchImages() {
        final DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.dateTimeNoMillis();
        pDialog.setMessage("Downloading images...");
        pDialog.show();
        StringRequest req = new StringRequest(pathToDownload,
                new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                Log.d(TAG, response);

                pDialog.hide();

                images.clear();
                try {
                    JSONObject responseObj = new JSONObject(response);
                    JSONObject embedded = responseObj.getJSONObject("_embedded");
                    JSONArray returnedData = embedded.getJSONArray("items");

                    for (int i = 0; i < returnedData.length(); i++) {
                        JSONObject object = returnedData.getJSONObject(i);
                        ImageYandexDisk image = new ImageYandexDisk();
                        image.setName(object.getString("name"));
                        image.setPreview(object.getString("preview"));
                        image.setSize(object.getInt("size"));
                        image.setDateTimeCreated(
                                DATE_FORMAT.parseDateTime(object.getString("created")));
                        image.setDateTimeModified(
                                DATE_FORMAT.parseDateTime(object.getString("modified")));
                        images.add(image);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
                //mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }
}
