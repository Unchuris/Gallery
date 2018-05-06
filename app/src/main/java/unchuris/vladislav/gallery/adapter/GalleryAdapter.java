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
import unchuris.vladislav.gallery.utils.IOnClickItem;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private ArrayList<String> imagesURL;

    private Context context;

    private final float thumbnailSize = 0.1f;

    private IOnClickItem callback;

    /**
     * Provide a reference to the views for each data item.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView thumbnail;

        public ViewHolder(final View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    public GalleryAdapter(final Context context, final ArrayList<String> images,
                          final IOnClickItem callback) {
        this.context = context;
        this.imagesURL = images;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Integer position = viewHolder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    callback.itemOnClick(position);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context)
                .load(imagesURL.get(position))
                .thumbnail(thumbnailSize)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_error_download))
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return imagesURL.size();
    }

}
