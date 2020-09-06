package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;

public class OnBoardAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    public OnBoardAdapter(Context context) {
        this.context = context;
    }

    private int image[] = {
            R.drawable.view_page1,
            R.drawable.view_page2,
            R.drawable.view_page3
    };

    private String title[] = {
            "Learn",
            "Create",
            "Enjoy"
    };
    private String moTa[] = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam."
    };

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dong_viewgager, container, false);

        ImageView imageView = v.findViewById(R.id.imageViewViewPager);
        TextView txtTitle = v.findViewById(R.id.textViewTitle);
        TextView txtViewPager = v.findViewById(R.id.textViewViewPager);

        imageView.setImageResource(image[position]);
        txtTitle.setText(title[position]);
        txtViewPager.setText(moTa[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
