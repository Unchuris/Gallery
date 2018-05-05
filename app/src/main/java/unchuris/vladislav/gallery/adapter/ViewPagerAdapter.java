package unchuris.vladislav.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import unchuris.vladislav.gallery.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * ViewPagerAdapter.
 */
public class ViewPagerAdapter extends PagerAdapter {
    /**
     * An array from the list of links on the images.
     */
    private ArrayList<String> imagesURL;

    /**
     * Context.
     */
    private Context context;

    /**
     * Size of thumbnail relative to original size.
     */
    private final float thumbnailSize = 0.1f;

    /**
     * Instantiate the LayoutInflater.
     */
    private LayoutInflater layoutInflater;

    /**
     * Constructor.
     * @param context context.
     * @param images array list of links on images.
     */
    public ViewPagerAdapter(final Context context, final ArrayList<String> images) {
        imagesURL = images;
        this.context = context;
    }

    /**
     * Create the page for the given position passed to it as an argument.
     * @param container viewGroup.
     * @param position position number.
     * @return object.
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.image_fullscreen_preview, container, false);

        ImageView imageViewPreview = view.findViewById(R.id.image_preview);

        Glide.with(context)
                .load(imagesURL.get(position))
                .thumbnail(thumbnailSize)
                .transition(withCrossFade())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageViewPreview);

        container.addView(view);

        return view;
    }

    /**
     * Get item count.
     * @return size.
     */
    @Override
    public int getCount() {
        return imagesURL.size();
    }

    /**
     * Checks whether the View passed representing the page is associated
     * with that key or not.
     * @param view view.
     * @param obj object.
     * @return view == obj.
     */
    @Override
    public boolean isViewFromObject(@NonNull final View view, @NonNull final Object obj) {
        return view == obj;
    }

    /**
     * Removes the page from the container for the given position.
     * @param container viewGroup.
     * @param position position number.
     * @param object object.
     */
    @Override
    public void destroyItem(@NonNull final ViewGroup container,
                            final int position,
                            final @NonNull Object object) {
        container.removeView((View) object);
    }
}
