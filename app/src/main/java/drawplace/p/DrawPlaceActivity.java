package drawplace.p;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

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
        DisplayMetrics metrics = new DisplayMetrics();
        FrameLayout canvas = findViewById(R.id.frameCanvas);
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        myCanvas.init(metrics);
        canvas.addView(myCanvas);

    }

    public void clearAll(View v){
        myCanvas.clearAll();
    }

    public void clearOne(View v){
        myCanvas.clearOne();
    }


    public void setColorWhite(View v) {
        myCanvas.setColors(0);
    }

    public void setColorYellow(View v) {
        myCanvas.setColors(1);
    }

    public void setColorGreen (View v) {
        myCanvas.setColors(2);
    }

    public void setColorBlue (View v) {
        myCanvas.setColors(3);
    }

    public void setColorRed(View v) {
        myCanvas.setColors(4);
    }
}

