package unchuris.vladislav.gallery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
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
 * DialogFragment for display image in full screen.
 */
public class SlideshowDialogFragment extends DialogFragment  {

    private ViewPager viewPager;

    private ArrayList<ImageYandexDisk> images;

    private ViewPagerAdapter viewPagerAdapter;

    /**
     * Parameter for get a full screen image.
     */
    private String fullscreenSize = "XXXL";

    /**
     * The number of helper for resizing.
     */
    private static final Integer RESIZING = 1024;

    private int selectedPosition = 0;

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

        if ((getArguments() != null) && (getArguments().getSerializable(MainActivity.IMAGES_RESOURCES) != null)) {
            images = (ArrayList<ImageYandexDisk>) getArguments().getSerializable(MainActivity.IMAGES_RESOURCES);
        }

        selectedPosition = getArguments() != null ? getArguments().getInt(MainActivity.POSITION) : 0;

        ArrayList<String> imagesURL = new ArrayList<>();

        for (ImageYandexDisk item : images) {
            imagesURL.add(item.getPreview(fullscreenSize));
        }

        viewPagerAdapter = new ViewPagerAdapter(getActivity(), imagesURL);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Button buttonInfo = v.findViewById(R.id.buttonInfo);
        buttonInfo.setOnClickListener(view ->
                showAlert(getContext(), images.get(selectedPosition)));

        Button buttonShare = v.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(view ->
                share(images.get(selectedPosition).getPreview(fullscreenSize)));

        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(final int position) {
        viewPager.setCurrentItem(position, false);
        displayNumber(selectedPosition);
    }

    private void displayNumber(final int position) {
        lblCount.setText((position + 1) + " " + getString(R.string.of) + " " + images.size());
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

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

    private void showAlert(final Context context, final ImageYandexDisk imageYandexDisk) {
        Integer style = android.R.style.Theme_Holo_Dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, style);
        builder.setTitle(R.string.titleAlertDialogFullscreen)
                .setMessage(getInfo(imageYandexDisk))
                .setCancelable(true)
                .setNegativeButton(R.string.buttonTextOK,
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.setOnShowListener(
                arg0 -> alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(getResources().getColor(R.color.colorFullscreenButton)));
        alert.show();
    }

    private String getInfo(final ImageYandexDisk image) {
        DateTimeFormatter longDate = DateTimeFormat.forPattern("d MMMM yyyy");
        String newLine = "\n\n";
        Integer size = image.getSize() / RESIZING;
        return "\n" + getString(R.string.fileText) + " " + image.getName() + newLine
                + getString(R.string.sizeText) + " " + size + getString(R.string.KB) + newLine
                + getString(R.string.timeCreation) + "\n" + longDate.print(image.getDateTimeCreated()) + newLine
                + getString(R.string.timeChange) + "\n" + longDate.print(image.getDateTimeModified()) + newLine
                ;
    }

    private void share(final String url) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
