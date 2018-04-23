package unchuris.vladislav.gallery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import unchuris.vladislav.gallery.R;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
