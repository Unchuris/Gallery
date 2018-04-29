package unchuris.vladislav.gallery.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.adapter.GalleryAdapter;
import unchuris.vladislav.gallery.utils.IClickListener;
import unchuris.vladislav.gallery.utils.RecyclerTouchListener;
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
public class MainActivity extends AppCompatActivity implements IResponseCallback<ImageYandexDisk> {
    /**
     * Public folder on Yandex disk.
     */
    private static final String PUBLIC_FOLDER_URL = "https://yadi.sk/d/2juJHwM13UjXGw";

    /**
     * Number of columns to display the images.
     */
    private static final Integer SPAN_COUNT = 2;

    /**
     * Instance the recyclerView.
     */
    private RecyclerView recyclerView;

    /**
     * Full link to the public folder.
     */
    private String pathToDownload;

    private ArrayList<ImageYandexDisk> images = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getApplicationContext(), SPAN_COUNT);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        IApiGenerationLink yd = new YandexDisk();

        pathToDownload = yd.getPublicLink(PUBLIC_FOLDER_URL,
                "preview_size=M&preview_crop=true&limit=200");
        IParsing<ImageYandexDisk> parsing = new ParsingResponse();
        Fetcher<ImageYandexDisk> iyd = new Fetcher<>(this, this);
        iyd.fetchImages(pathToDownload, parsing);

        addOnItemTouchListener();
    }

    /**
     * Instantiate the gallery adapter.
     * @param response ArrayList<Model> for postback.
     */
    @Override
    public void response(final ArrayList<ImageYandexDisk> response) {
        images = response;
        ArrayList<String> imagesURL = new ArrayList<>();
        for (ImageYandexDisk item : response) {
            imagesURL.add(item.getPreview());
        }
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imagesURL);
        recyclerView.setAdapter(adapter);
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
                bundle.putSerializable("images", images);
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
}
