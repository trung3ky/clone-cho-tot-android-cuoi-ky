package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class DanhMucAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<DanhMuc> arrayList;
    public String id =  "";

    public DanhMucAdapter(Context context, int layout, ArrayList<DanhMuc> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgDanhMuc;
        TextView txtDanhMuc;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.imgDanhMuc = (ImageView) convertView.findViewById(R.id.imageViewDanhMuc);
            holder.txtDanhMuc = (TextView) convertView.findViewById(R.id.textViewTenDanhMuc);



            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        final DanhMuc danhMuc = arrayList.get(position);
        String url = URL.urlAnh+danhMuc.getImgDanhMuc();
        Picasso.get().load(url).into(holder.imgDanhMuc);
        holder.txtDanhMuc.setText(danhMuc.getTenDanhMuc());

        holder.imgDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    id = String.valueOf(danhMuc.getId());
                Intent intent = new Intent(context, Activity_SanPham.class);
                intent.putExtra("id", id);
                intent.putExtra("SearchName", danhMuc.getTenDanhMuc());
                  context.startActivity(intent);
            }
        });
        return convertView;
    }


}
