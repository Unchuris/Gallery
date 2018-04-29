package unchuris.vladislav.gallery.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.adapter.ViewPagerAdapter;
import unchuris.vladislav.gallery.model.ImageYandexDisk;

/**
 * SlideshowDialogFragment.
 */
public class SlideshowDialogFragment extends DialogFragment {
    /**
     * Log tag.
     */
    private static final String TAG = SlideshowDialogFragment.class.getSimpleName();

    /**
     * Instantiate the viewPager.
     */
    private ViewPager viewPager;

    /**
     * An array from the images.
     */
    private ArrayList<ImageYandexDisk> images;

    /**
     * Instantiate the viewPagerAdapter.
     */
    private ViewPagerAdapter viewPagerAdapter;

    /**
     * Selected position.
     */
    private int selectedPosition = 0;

    /**
     * Get new Instance.
     * @return instance.
     */
    static SlideshowDialogFragment newInstance() {
        return new SlideshowDialogFragment();
    }

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        images = new ArrayList<>();

        if ((getArguments() != null) && (getArguments().getSerializable("images") != null)) {
            images = (ArrayList<ImageYandexDisk>) getArguments().getSerializable("images");
        }

        selectedPosition = getArguments() != null ? getArguments().getInt("position") : 0;

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        ArrayList<String> imagesURL = new ArrayList<>();

        for (ImageYandexDisk item : images) {
            imagesURL.add(item.getPreview("XXXL"));
        }

        viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagesURL);
        viewPager.setAdapter(viewPagerAdapter);

        setCurrentItem(selectedPosition);

        return v;
    }

    /**
     * Set current item.
     * @param position position number.
     */
    private void setCurrentItem(final int position) {
        viewPager.setCurrentItem(position, false);
    }

    /**
     * OnCreate.
     * @param savedInstanceState saved instance state.
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

}
