package com.raben.axfone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by raben on 26-Oct-17.
 */
public class SplashActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (TextView) findViewById(R.id.activity_splash_welcome_screen);
        iv = (ImageView) findViewById(R.id.activity_splash_image);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);

        final Intent i = new Intent(this, LoginActivity.class);

        Thread timer = new Thread(){
            public void run(){
                try {

                    /*
                    synchronized (this){
                        wait(3000);
                    }
                    */
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
                timer.start();
    }
}
