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
import unchuris.vladislav.gallery.utils.IClickListener;
import unchuris.vladislav.gallery.utils.IResponseCallback;
import unchuris.vladislav.gallery.utils.InternetConnection;
import unchuris.vladislav.gallery.utils.RecyclerTouchListener;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity implements IResponseCallback<ImageYandexDisk> {
    /**
     * Public folder on Yandex disk.
     */
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

    /**
     * key for accessing an array.
     */
    public static final String IMAGES_RESOURCES = "images";

    /**
     * Instance the recyclerView.
     */
    private RecyclerView recyclerView;

    /**
     * Full link to the public folder.
     */
    private String pathToDownload;

    /**
     * An array from the images.
     */
    private ArrayList<ImageYandexDisk> images = new ArrayList<>();

    /**
     * Instance the textView.
     */
    private TextView noInternet;

    /**
     * Button to reload.
     */
    private Button reloadButton;

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

        addOnItemTouchListener();
    }

    private void initDisplayParams() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = SPAN_COUNT_LANDSCAPE;
        }
    }

    /**
     * InitRecycleView.
     */
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
                noInternet = findViewById(R.id.no_internet);
                noInternet.setVisibility(View.VISIBLE);
                reloadButton.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setAdapter(
                    new GalleryAdapter(getApplicationContext(), getImagesURL(img))
            );
        }
    }

    /**
     * Reload download.
     * @param view view.
     */
    public void reloadDownload(final View view) {
        if (InternetConnection.hasConnection(this)) {
            noInternet.setVisibility(View.GONE);
            reloadButton.setVisibility(View.GONE);
            download();
        } else {
            Toast myToast = Toast.makeText(this, R.string.connectionError, Toast.LENGTH_SHORT);
            myToast.show();
        }
    }

    /**
     * Get imagesURL
     * @param img array from the images.
     * @return list.
     */
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

    /**
     * Download the list of images.
     */
    private void download() {
        IApiGenerationLink yd = new YandexDisk();
        pathToDownload = yd.getPublicLink(PUBLIC_FOLDER_URL,
                "preview_size=M&preview_crop=true&limit=200");
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
                new GalleryAdapter(getApplicationContext(), getImagesURL(response))
        );
    }

    /**
     * RecyclerView on item touch listener.
     */
    private void addOnItemTouchListener() {
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(
                getApplicationContext(), recyclerView, new IClickListener() {
            /**
             * Onclick.
             * @param view view.
             * @param position position number.
             */
            @Override
            public void onClick(final View view, final int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(IMAGES_RESOURCES, images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            /**
             * On long click.
             * @param view view.
             * @param position position number.
             */
            @Override
            public void onLongClick(final View view, final int position) {

            }
        }));
    }

    /**
     * On save instance state.
     * @param outState bundle.
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(IMAGES_RESOURCES, images);
    }
}
