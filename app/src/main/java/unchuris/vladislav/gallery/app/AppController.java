package unchuris.vladislav.gallery.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Application controller for managing requests using the volley library.
 */
public class AppController extends Application {
    /**
     * Default tag for associate with request.
     */
    private static final String TAG = AppController.class
            .getSimpleName();

    /**
     * Instantiate the RequestQueue.
     */
    private RequestQueue mRequestQueue;

    /**
     * Instance the AppController.
     */
    private static AppController mInstance;

    /**
     * On create.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Get instance.
     * @return instance.
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Get request queue.
     * @return request queue.
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Add to request queue and set up tag.
     * @param req request.
     * @param tag object for associate with request.
     * @param <T> type of request. (JsonArrayRequest, StringRequest).
     */
    public <T> void addToRequestQueue(final Request<T> req, final String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Add to request queue.
     * @param req request.
     * @param <T> type of request. (JsonArrayRequest, StringRequest).
     */
    public <T> void addToRequestQueue(final Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancel pending requests.
     * @param tag associated with the request.
     */
    public void cancelPendingRequests(final Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
