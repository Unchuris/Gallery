package unchuris.vladislav.gallery.utils;

import java.util.ArrayList;

/**
 * Interface for response callback.
 * @param <T> model.
 */
public interface IResponseCallback<T> {
    /**
     * Callback.
     * @param response ArrayList<Model> for  postback.
     */
    void response(ArrayList<T> response);

    /**
     * Callback.
     * @param message message about errors.
     */
    void errorMessage(String message);
}
