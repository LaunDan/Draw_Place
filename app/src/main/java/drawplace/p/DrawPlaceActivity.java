package drawplace.p;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class DrawPlaceActivity extends AppCompatActivity {
    //todo make ads for something like picture or just thank them for view ads
    //todo make in down screen some like swiped menu of color and light of color 

    private MyCanvas myCanvas;
    private String currentNameOfImage;
    private EditText input;
    final int IMAGE = 1;
    private RewardedAd mAd;
    private static final String TAG = "MainActivity";

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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        loadAd();


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        }


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
                    case R.id.save:
                        savePicture();
                        return true;

                    case R.id.insert:
                        insertImage();
                        return true;

                    case R.id.ads:
                        showAds();
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
        saveDialog.setMessage("Enter name of image.");
        input = new EditText(this);
        saveDialog.setView(input);
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentNameOfImage = input.getText().toString();
                if (currentNameOfImage.length() <= 2) {
                    currentNameOfImage = random();
                }
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

    private void saveBitmap(Bitmap bitmap) {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        } else {

            File file = Environment.getExternalStorageDirectory();

            File newFile = new File(file, currentNameOfImage + ".jpg");

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(DrawPlaceActivity.this,
                        "Image " + ",," + currentNameOfImage + "''" + " saved!",
                        Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(DrawPlaceActivity.this,
                        "Something wrong: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void insertImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);
        } else {

            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = {MediaStore.Images.Media.DATA};
            assert pickedImage != null;
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            assert cursor != null;
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

            Bitmap rotatedBitmap = rotateBitmap(bitmap, 90);
            // Do something with the bitmap
            DisplayMetrics metrics = myCanvas.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = ((metrics.heightPixels / 100) * 80);


            int widthI = rotatedBitmap.getWidth();
            int heightI = rotatedBitmap.getHeight();

            myCanvas.frameToDraw = new Rect(0, 0, widthI, heightI);
            myCanvas.whereToDraw = new RectF(0, 150, width, height);

            myCanvas.imageBitmap = rotatedBitmap;

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void showAds() {
        if (this.mAd.isLoaded()) {
            RewardedAdCallback callback = new RewardedAdCallback() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    Log.i(TAG, "onUserEarnedReward");
                }

                @Override
                public void onRewardedAdFailedToShow(int i) {
                    super.onRewardedAdFailedToShow(i);
                    Log.i(TAG, "onRewardedAdClosed");
                }

                @Override
                public void onRewardedAdClosed() {
                    super.onRewardedAdClosed();
                    loadAd();
                }
            };
            this.mAd.show(this, callback);
        } else {
            loadAd();
        }
    }

    private void loadAd() {
        this.mAd = new RewardedAd(this, "ca-app-pub-8565963453586256/7582350354");
        RewardedAdLoadCallback callback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
                Log.i(TAG, "onRewardedAdFailedToLoad");
            }

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();
                Log.i(TAG, "onRewardedAdLoaded");
            }
        };
        this.mAd.loadAd(new AdRequest.Builder().build(), callback);
    }
}