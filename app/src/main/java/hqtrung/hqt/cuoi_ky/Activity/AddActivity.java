package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Chon_Anh;
import hqtrung.hqt.cuoi_ky.fragment.Add_chon_danh_muc;

public class AddActivity extends AppCompatActivity {
    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd, new Add_chon_danh_muc()).commit();
    }


}
