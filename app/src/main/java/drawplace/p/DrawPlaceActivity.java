package drawplace.p;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import java.util.UUID;


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
                        myCanvas.setDrawingCacheEnabled(true);
                        String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(), myCanvas.getDrawingCache(), UUID.randomUUID().toString()+".png", "drawing");
                        if (imgSaved !=null){
                            Toast savedToast = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
                            savedToast.show();
                        } else{
                            Toast unSaved = Toast.makeText(getApplicationContext(), "Opps, error. Image not saved, try again!", Toast.LENGTH_LONG);
                            unSaved.show();
                        }
                        myCanvas.destroyDrawingCache();
                        return true;

                    case R.id.background:
                        myCanvas.changeBackground();
                        return true;

                    case R.id.end:
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

    public void setColorBlack(View v) {
        myCanvas.setColors(5);
    }

    public void menu(View v){

    }
}

