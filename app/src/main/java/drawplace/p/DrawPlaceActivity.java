package drawplace.p;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;


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

        SeekBar currentWidth = findViewById(R.id.widthValue);
        currentWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myCanvas.changeWidth(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        finish();
                        return true;
                        default:
                            return false; }
            }
        });

    }




    public void clearAll(View v) {
        myCanvas.clearAll();
    }

    public void clearOne(View v) {
        myCanvas.clearOne();
    }



    public void setColorWhite(View v) {
        myCanvas.setColors(0);
    }

    public void setColorYellow(View v) {
        myCanvas.setColors(1);
    }

    public void setColorGreen(View v) {
        myCanvas.setColors(2);
    }

    public void setColorBlue(View v) {
        myCanvas.setColors(3);
    }

    public void setColorRed(View v) {
        myCanvas.setColors(4);
    }

    public void menu(View v){

    }
}

