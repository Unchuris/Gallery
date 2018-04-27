package unchuris.vladislav.gallery.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.adapter.GalleryAdapter;
import unchuris.vladislav.gallery.api.IApiGenerationLink;
import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.api.yandex.ParsingResponse;
import unchuris.vladislav.gallery.api.yandex.YandexDisk;
import unchuris.vladislav.gallery.model.ImageYandexDisk;
import unchuris.vladislav.gallery.utils.Fetcher;
import unchuris.vladislav.gallery.utils.IResponseCallback;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity implements IResponseCallback<ImageYandexDisk>{
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

    private GalleryAdapter mAdapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        IApiGenerationLink yd = new YandexDisk();
        pathToDownload = yd.getPublicLink(PUBLIC_FOLDER_URL, "preview_size=M&preview_crop=true");

        final IParsing<ImageYandexDisk> parsing = new ParsingResponse();
        Fetcher<ImageYandexDisk> iyd = new Fetcher<>(this);
        iyd.fetchImages(pathToDownload, parsing);
    }

    @Override
    public void response(ArrayList<ImageYandexDisk> response) {
        ArrayList<String> imagesURL = new ArrayList<>();
        for(ImageYandexDisk item : response){
            imagesURL.add(item.getPreview());
        }
        mAdapter = new GalleryAdapter(getApplicationContext(), imagesURL);
        recyclerView.setAdapter(mAdapter);
    }
}
