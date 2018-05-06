package unchuris.vladislav.gallery.utils;

import java.util.ArrayList;

/**
 * Interface for response callback.
 * @param <T> model.
 */
public interface IResponseCallback<T> {

    void response(ArrayList<T> response);

    void errorMessage(String message);
}
