package unchuris.vladislav.gallery.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.adapter.GalleryAdapter;
import unchuris.vladislav.gallery.api.IApiGenerationLink;
import unchuris.vladislav.gallery.api.IParsing;
import unchuris.vladislav.gallery.api.yandex.ParsingResponse;
import unchuris.vladislav.gallery.api.yandex.YandexDisk;
import unchuris.vladislav.gallery.model.ImageYandexDisk;
import unchuris.vladislav.gallery.utils.Fetcher;
import unchuris.vladislav.gallery.utils.IOnClickItem;
import unchuris.vladislav.gallery.utils.IResponseCallback;
import unchuris.vladislav.gallery.utils.InternetConnection;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity implements IResponseCallback<ImageYandexDisk>, IOnClickItem {

    private static final String PUBLIC_FOLDER_URL = "https://yadi.sk/d/2juJHwM13UjXGw";

    /**
     * Number of columns to display the images on the orientation portrait.
     */
    private static final Integer SPAN_COUNT_PORTRAIT = 2;

    /**
     * Number of columns to display the images on the orientation landscape.
     */
    private static final Integer SPAN_COUNT_LANDSCAPE = 2 * SPAN_COUNT_PORTRAIT;

    /**
     * Number of columns to display the images.
     */
    private Integer spanCount = SPAN_COUNT_PORTRAIT;

    public static final String IMAGES_RESOURCES = "images";

    public static final String POSITION = "position";

    private RecyclerView recyclerView;

    private TextView textView;

    private Button reloadButton;

    /**
     * Download limit.
     */
    private final Integer limit = 100;

    private String pathToDownload;

    private ArrayList<ImageYandexDisk> images = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initDisplayParams();

        recyclerView = findViewById(R.id.recycler_view);
        initRecycleView();

        if (savedInstanceState != null) {
            images = savedInstanceState.getParcelableArrayList(IMAGES_RESOURCES);
        }

        init(images);
    }

    /**
     * Set the number of columns for the gallery.
     */
    private void initDisplayParams() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = SPAN_COUNT_LANDSCAPE;
        }
    }

    private void initRecycleView() {
        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getApplicationContext(), spanCount);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * RecyclerView set adapter.
     * @param img array from the images.
     */
    private void init(final ArrayList<ImageYandexDisk> img) {
        if (img != null && img.isEmpty()) {
            if (InternetConnection.hasConnection(this)) {
                download();
            } else {
                reloadButton = findViewById(R.id.reload_button);
                textView = findViewById(R.id.message);
                textView.setVisibility(View.VISIBLE);
                reloadButton.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setAdapter(
                    new GalleryAdapter(getApplicationContext(), getImagesURL(img), this)
            );
        }
    }

    public void reloadDownload(final View view) {
        if (InternetConnection.hasConnection(this)) {
            textView.setVisibility(View.GONE);
            reloadButton.setVisibility(View.GONE);
            download();
        } else {
            Toast myToast = Toast.makeText(this, R.string.connectionError, Toast.LENGTH_SHORT);
            myToast.show();
        }
    }

    private ArrayList<String> getImagesURL(final ArrayList<ImageYandexDisk> img) {
        if (img == null) {
            return new ArrayList<>();
        }
        ArrayList<String> imagesURL = new ArrayList<>();

        for (ImageYandexDisk item : img) {
            imagesURL.add(item.getPreview());
        }

        return imagesURL;
    }

    private void download() {
        IApiGenerationLink yd = new YandexDisk();
        pathToDownload = yd.getPublicLink(PUBLIC_FOLDER_URL,
                yd.getQueryParameters("M", limit, "modified"));
        IParsing<ImageYandexDisk> parsing = new ParsingResponse();
        Fetcher<ImageYandexDisk> iyd = new Fetcher<>(this, this);
        // When the download finishes calling "response".
        iyd.fetchImages(pathToDownload, parsing);
    }

    /**
     * Instantiate the gallery adapter.
     * @param response ArrayList<Model> for postback.
     */
    @Override
    public void response(final ArrayList<ImageYandexDisk> response) {
        images = response;
        recyclerView.setAdapter(
                new GalleryAdapter(getApplicationContext(), getImagesURL(response), this)
        );
        listIsEmpty(response);
    }

    @Override
    public void errorMessage(final String message) {
        textView = findViewById(R.id.message);
        textView.setText(message);
        textView.setVisibility(View.VISIBLE);
    }

    private void listIsEmpty(final ArrayList<ImageYandexDisk> img) {
        if (img.isEmpty()) {
            textView = findViewById(R.id.message);
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.isEmpty);
        }
    }

    @Override
    public void itemOnClick(final int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IMAGES_RESOURCES, images);
        bundle.putInt(POSITION, position);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
        newFragment.setArguments(bundle);
        newFragment.show(ft, "slideshow");
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(IMAGES_RESOURCES, images);
    }
}
