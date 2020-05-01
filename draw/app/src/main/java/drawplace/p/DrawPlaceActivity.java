package drawplace.p;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawPlaceActivity extends AppCompatActivity {

    private TextView coords;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_place);

        coords = findViewById(R.id.coord);
    }
}

