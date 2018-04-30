package unchuris.vladislav.gallery.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;
import unchuris.vladislav.gallery.adapter.ViewPagerAdapter;
import unchuris.vladislav.gallery.model.ImageYandexDisk;

/**
 * SlideshowDialogFragment.
 */
public class SlideshowDialogFragment extends DialogFragment  {
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

    /**
     * Shows the number of images and the total number.
     */
    private TextView lblCount;

    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        lblCount = v.findViewById(R.id.lbl_count);
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
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Button newGameButton = v.findViewById(R.id.buttonInfo);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showAlert(getContext(), images.get(selectedPosition));
            }
        });

        setCurrentItem(selectedPosition);

        return v;
    }

    /**
     * Set current item.
     * @param position position number.
     */
    private void setCurrentItem(final int position) {
        viewPager.setCurrentItem(position, false);
        displayNumber(selectedPosition);
    }

    /**
     * Shows the number of images and the total number.
     * @param position position number.
     */
    private void displayNumber(final int position) {
        lblCount.setText((position + 1) + " " + getString(R.string.of) + " " + images.size());
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

    /**
     * On page change listener.
     */
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener =
            new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            selectedPosition = position;
            displayNumber(position);
        }

        @Override
        public void onPageScrolled(final int arg0, final float arg1, final int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(final int arg0) {

        }
    };

    /**
     * Show alertDialog.
     * @param context context.
     * @param imageYandexDisk image.
     */
    private void showAlert(final Context context, final ImageYandexDisk imageYandexDisk) {
        Integer style = android.R.style.Theme_Holo_Dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, style);
        builder.setTitle("Детали: ")
                .setMessage(getInfo(imageYandexDisk))
                .setCancelable(true)
                .setNegativeButton("OK",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setOnShowListener(
                arg0 -> alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(getResources().getColor(R.color.colorFullscreenButton)));
        alert.show();
    }

    /**
     * Get meta-information.
     * @param image image.
     * @return image info.
     */
    private String getInfo(final ImageYandexDisk image) {
        DateTimeFormatter longDate = DateTimeFormat.forPattern("d MMMM yyyy");
        String newLine = "\n\n";
        Integer size = image.getSize() / 1024;
        return "\nФайл: " + image.getName() + newLine
                + "Размер: " + size + " KB" + newLine
                + "Время создания: \n" + longDate.print(image.getDateTimeCreated()) + newLine
                + "Время изменения: \n" + longDate.print(image.getDateTimeModified()) + newLine
                ;
    }

}
