package drawplace.p;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.UUID;


public class DrawPlaceActivity extends AppCompatActivity {

    private MyCanvas myCanvas;
    public OutputStream outputStream;

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
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save: //TODO get work saving
                        savePicture();
                        return true;

                    case R.id.background:
                        myCanvas.changeBackground();
                        return true;

                    case R.id.end:
                        finish();
                        return true;

                    default:
                        return false;
                }
            }
        });

    }

    public void clearAll(View v) {
        myCanvas.clearAll();
    }

    public void clearOne(View v) {
        myCanvas.clearOne();
    }

    //TODO change colors changer

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

    private void savePicture() {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveBitmap(myCanvas.mBitmap);
            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    private String saveBitmap(Bitmap bm) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/draw/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }
}

