package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.adapter.OnBoardAdapter;

public class OnBoardActivity extends AppCompatActivity {

    ViewPager viewPager;
    Button btnskip, btnnext;
    LinearLayout dotlayout;
    OnBoardAdapter adapter;
    TextView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        AnhXa();
    }

    private void AnhXa() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btnskip = (Button) findViewById(R.id.buttonSkip);
        btnnext = (Button) findViewById(R.id.buttonNext);
        dotlayout = (LinearLayout) findViewById(R.id.dotslayout);
        adapter = new OnBoardAdapter(this);

            addDots(0);
        viewPager.addOnPageChangeListener(listener);
        viewPager.setAdapter(adapter);


        btnnext.setOnClickListener(v->{
            if (btnnext.getText().toString().equals("Next")){
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }else{
                startActivity(new Intent(OnBoardActivity.this, AuthActivity.class));
                finish();
            }
        });

        btnskip.setOnClickListener(v->{
            viewPager.setCurrentItem(viewPager.getCurrentItem()+2);
        });



    }
    private void addDots(int position){
        dotlayout.removeAllViews();
        dots = new TextView[3];
        for (int i = 0; i< dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            dotlayout.addView(dots[i]);
        }
        if (dots.length >0){
            dots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            if (position == 0){
                btnskip.setVisibility(View.VISIBLE);
                btnskip.setEnabled(true);
                btnnext.setText("Next");
            }else if(position == 1) {
                btnskip.setVisibility(View.GONE);
                btnskip.setEnabled(false);
                btnnext.setText("Next");
            }else{
                btnskip.setVisibility(View.GONE);
                btnskip.setEnabled(false);
                btnnext.setText("Finish");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
