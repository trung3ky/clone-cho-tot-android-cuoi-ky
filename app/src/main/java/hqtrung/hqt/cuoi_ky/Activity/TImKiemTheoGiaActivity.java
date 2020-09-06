package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import hqtrung.hqt.cuoi_ky.R;

public class TImKiemTheoGiaActivity extends AppCompatActivity {

    RangeSeekBar rangeSeekBar;
    TextView txtMin, txtMax, txtBoLoc;
    ImageView imgClose;
    Button btnGia;

    int longval1 = 0 ;
    int longval2 = 1000000000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_im_kiem_theo_gia);

        AnhXa();
    }

    private void AnhXa() {
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.rangeseekbar);
        txtMin = (TextView) findViewById(R.id.TextViewMin);
        imgClose = (ImageView) findViewById(R.id.closeGia);
        btnGia = (Button) findViewById(R.id.buttontimkiemgia);
        txtMax = (TextView) findViewById(R.id.TextViewMax);
        txtBoLoc = (TextView) findViewById(R.id.BoLoc);

        txtBoLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.giamax = "";
                ActivityHienThiTimKiem.giamin = "";
                startActivity(new Intent(TImKiemTheoGiaActivity.this, ActivityHienThiTimKiem.class));
            }
        });

        btnGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.giamin = longval1+"";
                ActivityHienThiTimKiem.giamax = longval2+"";
                startActivity(new Intent(TImKiemTheoGiaActivity.this, ActivityHienThiTimKiem.class));
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rangeSeekBar.setSelectedMinValue(1);
        rangeSeekBar.setSelectedMaxValue(100);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number numberMin = bar.getSelectedMinValue();
                Number numberMax = bar.getSelectedMaxValue();

                int min = (int) minValue;
                int max = (int) maxValue;

                String giaMin = min*(10000000)+"";
                String giaMax = max*(10000000)+"";


                longval1 = Integer.parseInt(giaMin);
                longval2 = Integer.parseInt(giaMax);

                DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatter.applyPattern("#,###,###,###");
                String formattedString1 = formatter.format(longval1);
                String formattedString2 = formatter.format(longval2);


                txtMin.setText(formattedString1+" đ");
                txtMax.setText(formattedString2+" đ");
            }
        });

    }
}
