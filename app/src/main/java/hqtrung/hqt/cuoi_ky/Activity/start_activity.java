package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import hqtrung.hqt.cuoi_ky.R;

public class start_activity extends AppCompatActivity {

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_activity);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isFirstTime();
            }
        }, 2000);

    }

    private void isFirstTime(){
        SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
        if (isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            //start sigin
            startActivity(new Intent(start_activity.this, OnBoardActivity.class));
            finish();
        }else{
            startActivity(new Intent(start_activity.this, MainActivity.class));
            finish();

        }
    }
}
