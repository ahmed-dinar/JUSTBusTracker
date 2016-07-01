package com.ahmeddinar.justbustracker.splashscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.ahmeddinar.justbustracker.MainActivity;
import com.ahmeddinar.justbustracker.R;
import com.ahmeddinar.justbustracker.utils.ConnectionUtils;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(!ConnectionUtils.isNetworkAvailable(this)){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Check your Internet Connection & Try again")
                    .setTitle("Connection Error")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }else {

            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(1000); //1sec = 1000ms
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            };
            timerThread.start();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}