package unchuris.vladislav.gallery.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import unchuris.vladislav.gallery.R;

/**
 * Activity for display the main screen, which contains an image gallery.
 */
public class MainActivity extends AppCompatActivity {

    /**
     *  Public folder on Yandex disk.
     */
    public static final String PUBLIC_FOLDER_URL = "https://yadi.sk/d/2juJHwM13UjXGw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
