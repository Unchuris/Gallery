package unchuris.vladislav.gallery.utils;

import android.view.View;

/**
 * Interface for click listener.
 */
public interface IClickListener {
    /**
     * Onclick.
     * @param view view.
     * @param position position number.
     */
    void onClick(View view, int position);

    /**
     * On long click.
     * @param view view.
     * @param position position number.
     */
    void onLongClick(View view, int position);
}
