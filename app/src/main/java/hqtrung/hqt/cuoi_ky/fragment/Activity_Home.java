package hqtrung.hqt.cuoi_ky.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Activity_Search;
import hqtrung.hqt.cuoi_ky.Activity.Chat_Activity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.ChucNangAdapter;
import hqtrung.hqt.cuoi_ky.adapter.DanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.ChucNang;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class Activity_Home extends Fragment {
    ViewFlipper viewFlipper;
    TextView txtTiemKiem;
    //Chức năng
    RecyclerView recyclerView;
    ArrayList<ChucNang> chucNangArrayList;
    ChucNangAdapter adapterChucNang;
    //Danh mục
    GridView gridView;
    ArrayList<DanhMuc> danhMucArrayList;
    DanhMucAdapter adapterDanhMuc;

    String urldanhmuc = URL.urlDanhMuc;
    View view;
    ImageView imgChat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_home, container, false);

        AnhXa();

        //Chức năng

        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerviewChucNangHome);
        chucNangArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterChucNang = new ChucNangAdapter(getActivity(), chucNangArrayList);
        recyclerView.setAdapter(adapterChucNang);
        AddChucNang();
        //-----------

        //Danh mục
        gridView = (GridView) view.findViewById(R.id.gridview);
        danhMucArrayList = new ArrayList<>();

        adapterDanhMuc = new DanhMucAdapter(getActivity(), R.layout.dong_danh_muc, danhMucArrayList);
        gridView.setAdapter(adapterDanhMuc);

        getDanhMuc(urldanhmuc);
        //-----------
        ActionViewFlipper();


        return view;
    }

    private void AnhXa() {
        imgChat = (ImageView) view.findViewById(R.id.Chat);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.viewflipperhome);
        txtTiemKiem = (TextView) view.findViewById(R.id.TextViewTimKiem);
        txtTiemKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(getActivity(), Activity_Search.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Chat_Activity.class));
            }
        });
    }

    private void getDanhMuc(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObjectt = response.getJSONObject(i);
                        danhMucArrayList.add(new DanhMuc(
                                jsonObjectt.getInt("id"),
                                jsonObjectt.getString("anh"),
                                jsonObjectt.getString("ten_danh_muc")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterDanhMuc.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "lỗi" + error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void AddChucNang() {
        chucNangArrayList.add(new ChucNang(R.drawable.star, "Ưu đãi mới", 1));
        chucNangArrayList.add(new ChucNang(R.drawable.tym, "Tin rao đã lưu", 2));
        chucNangArrayList.add(new ChucNang(R.drawable.wheel, "Vòng quay may mắn", 3));
        chucNangArrayList.add(new ChucNang(R.drawable.tickmark, "tìm kiếm đã lưu", 4));

        adapterChucNang.notifyDataSetChanged();
    }

    private void ActionViewFlipper() {
        ArrayList<Integer> mangquangcao = new ArrayList<>();
        mangquangcao.add(R.drawable.slide6);
        mangquangcao.add(R.drawable.slide3);
        mangquangcao.add(R.drawable.slide1);
        for (int i = 0 ; i < mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getActivity().getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        Animation slide_in_right = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_in_right);
        Animation slide_out_right = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(slide_in_right);
        viewFlipper.setOutAnimation(slide_out_right);

    }



}
