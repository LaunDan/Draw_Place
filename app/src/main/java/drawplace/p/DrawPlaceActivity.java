package drawplace.p;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DrawPlaceActivity extends AppCompatActivity {

    private MyCanvas myCanvas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_draw_place);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        myCanvas = new MyCanvas(this);
        myCanvas.setColors(0);
        FrameLayout canvas = findViewById(R.id.frameCanvas);
        canvas.addView(myCanvas);
    }

//TODO methods for change colors

    //TODO create buttons for restart view and oneStepBack
}

