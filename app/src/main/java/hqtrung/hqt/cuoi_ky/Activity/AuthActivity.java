package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.SignInFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAuth, new SignInFragment()).commit();
    }
}
