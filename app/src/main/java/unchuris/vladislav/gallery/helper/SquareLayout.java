package unchuris.vladislav.gallery.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * SquareLayout.
 */
public class SquareLayout extends RelativeLayout {
    /**
     * Constructor.
     * @param context context.
     */
    public SquareLayout(final Context context) {
        super(context);
    }

    /**
     * Constructor.
     * @param context context.
     * @param attrs attributes.
     */
    public SquareLayout(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * @param context context.
     * @param attrs attributes.
     * @param defStyleAttr default style attributes.
     */
    public SquareLayout(final Context context, final AttributeSet attrs,
                        final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Constructor
     * @param context context.
     * @param attrs attributes.
     * @param defStyleAttr default style attributes.
     * @param defStyleRes default style resource.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SquareLayout(final Context context, final AttributeSet attrs,
                        final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * Set a square layout.
     * @param widthMeasureSpec width.
     * @param heightMeasureSpec height.
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
