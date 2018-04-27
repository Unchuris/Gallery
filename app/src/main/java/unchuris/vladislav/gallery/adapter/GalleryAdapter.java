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
 *
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    /**
     *
     */
    private ArrayList<String> imagesURL;
    /**
     *
     */
    private Context mContext;

    /**
     *
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    /**
     *
     * @param context
     * @param images
     */
    public GalleryAdapter(final Context context, final ArrayList<String> images) {
        mContext = context;
        this.imagesURL = images;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_thumbnail, parent, false);

        return new MyViewHolder(itemView);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imagesURL.get(position))
                .thumbnail(0.5f)
                .transition(withCrossFade())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.thumbnail);
    }

    /**
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return imagesURL.size();
    }

}
