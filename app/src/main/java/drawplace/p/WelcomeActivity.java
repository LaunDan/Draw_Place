package drawplace.p;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);



        ImageView imgvwSpsoa2 = findViewById(R.id.imageView2);
        Animation animSpsoaLogo2 = AnimationUtils.loadAnimation(this, R.anim.welcome_sc);
        ImageView imgvwSpsoa1 = findViewById(R.id.imageView);
        Animation animSpsoaLogo1 = AnimationUtils.loadAnimation(this, R.anim.welcome_sc2);
        imgvwSpsoa1.startAnimation(animSpsoaLogo1);
        imgvwSpsoa2.startAnimation(animSpsoaLogo2);
        Thread thrdWlcmscrnDelay = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    startActivity(new Intent(getApplicationContext(), DrawPlaceActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        thrdWlcmscrnDelay.start();
    }
}