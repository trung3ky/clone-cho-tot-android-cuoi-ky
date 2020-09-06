package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hqtrung.hqt.cuoi_ky.R;

public class AboutUs_Activity extends AppCompatActivity {
    ImageView backAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_);
        backAboutUs = (ImageView) findViewById(R.id.backAboutUs);
        backAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
