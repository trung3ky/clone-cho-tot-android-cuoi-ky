package hqtrung.hqt.cuoi_ky.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import hqtrung.hqt.cuoi_ky.R;

public class Add_chon_Loai_Dang_Tin extends Fragment {
    public static String LoaDangTin;
    public static String LoaDangTin1;

    View view;
    ImageView imgLDT;
    RadioGroup radioGroup;
    RadioButton rbMua, rbBan;
    LinearLayout layoutLDM;
    TextView txtLDM;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_loai_dang_tin, container, false);

        imgLDT = (ImageView) view.findViewById(R.id.ImageViewAddLDT);
        rbBan = (RadioButton) view.findViewById(R.id.radioBan);
        rbMua = (RadioButton) view.findViewById(R.id.radioMua);

        imgLDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_loai_danh_muc();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        rbMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaDangTin = "Cần mua";
                LoaDangTin1 = "canmua";
                Fragment fragment = new Add_Dia_Chi();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });
        rbBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaDangTin = "Cần bán";
                LoaDangTin1 = "canban";
                Fragment fragment = new Add_Dia_Chi();

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }



}
