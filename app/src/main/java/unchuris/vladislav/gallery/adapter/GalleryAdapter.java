package unchuris.vladislav.gallery.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
 * GalleryAdapter.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
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
    private static final float THUMBNAIL_SIZE = 0.1f;

    /**
     * Provide a reference to the views for each data item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * ImageView.
         */
        public ImageView thumbnail;

        /**
         * Constructor.
         * @param view view.
         */
        public ViewHolder(final View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    /**
     * Constructor.
     * @param context context.
     * @param images array list of links on images.
     */
    public GalleryAdapter(final Context context, final ArrayList<String> images) {
        this.context = context;
        this.imagesURL = images;
    }

    /**
     * Create new views (invoked by the layout manager).
     * @param parent ViewGroup.
     * @param viewType view type.
     * @return ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Replace the contents of a view (invoked by the layout manager).
     * @param holder holder.
     * @param position position.
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context)
                .load(imagesURL.get(position))
                .thumbnail(THUMBNAIL_SIZE)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_error_download))
                .into(holder.thumbnail);
    }

    /**
     * Get item count.
     * @return size.
     */
    @Override
    public int getItemCount() {
        return imagesURL.size();
    }

}
