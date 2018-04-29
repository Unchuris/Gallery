package unchuris.vladislav.gallery.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView touch listener.
 */
public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    /**
     * Instantiate the gesture detector.
     */
    private GestureDetector gestureDetector;

    /**
     * Instantiate the IClickListener.
     */
    private IClickListener clickListener;

    /**
     * Constructor.
     * @param context context.
     * @param recyclerView recyclerView.
     * @param clickListener click listener.
     */
    public RecyclerTouchListener(final Context context, final RecyclerView recyclerView,
                                 final IClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            /**
             * On single tap up.
             * @param e motion event.
             * @return true.
             */
            @Override
            public boolean onSingleTapUp(final MotionEvent e) {
                return true;
            }

            /**
             * On long press.
             * @param e motion event.
             */
            @Override
            public void onLongPress(final MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    /**
     * On intercept touch event.
     * @param recyclerView recyclerView
     * @param e motion event.
     * @return false.
     */
    @Override
    public boolean onInterceptTouchEvent(final RecyclerView recyclerView, final MotionEvent e) {

        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, recyclerView.getChildAdapterPosition(child));
        }
        return false;
    }

    /**
     * On touch event.
     * @param recyclerView recyclerView.
     * @param e motion event.
     */
    @Override
    public void onTouchEvent(final RecyclerView recyclerView, final MotionEvent e) {

    }

    /**
     * On request disallow intercept touch event.
     * @param disallowIntercept boolean.
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(final boolean disallowIntercept) {

    }
}
