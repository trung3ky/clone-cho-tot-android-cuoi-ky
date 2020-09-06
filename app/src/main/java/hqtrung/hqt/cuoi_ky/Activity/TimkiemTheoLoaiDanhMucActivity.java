package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddLoaiDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TimKiemTheoDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TimKiemTheoLoaiDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class TimkiemTheoLoaiDanhMucActivity extends AppCompatActivity {
    ImageView imgClose;
    RecyclerView recyclerViewLoaiDanhMuc;
    RelativeLayout layoutAll;
    public static TextView txtall;

    ArrayList<LoaiDanhMuc> arrayList;
    TimKiemTheoLoaiDanhMucAdapter adapter;

    String id, ten;
    String UrlLoaiDM = URL.urlSanPham;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem_theo_loai_danh_muc);

        Intent intent = getIntent();
        id =intent.getStringExtra("danhmuc");
        ten = intent.getStringExtra("tendanhmuc");

        AnhXa();
    }

    private void AnhXa() {
        imgClose = (ImageView) findViewById(R.id.closeLoai);
        layoutAll = (RelativeLayout) findViewById(R.id.layouttatcaLoai);
        recyclerViewLoaiDanhMuc = (RecyclerView) findViewById(R.id.RecyclerviewTheoLoaiDanhMuc);
        txtall = (TextView) findViewById(R.id.textviewTatCaLoai);

        layoutAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.AllDanhMuc = "";
                ActivityHienThiTimKiem.AllLoaiDanhMuc = "tatca";
                ActivityHienThiTimKiem.loaidanhmuc = "";
                startActivity(new Intent(TimkiemTheoLoaiDanhMucActivity.this, ActivityHienThiTimKiem.class));
            }
        });

        txtall.setText("Tất cả "+ten);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewLoaiDanhMuc.setLayoutManager(linearLayoutManager);

        adapter =  new TimKiemTheoLoaiDanhMucAdapter(this, arrayList);
        recyclerViewLoaiDanhMuc.setAdapter(adapter);


        TheoLoaiDanhMuc(UrlLoaiDM);

    }

    private void TheoLoaiDanhMuc(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("data");
                            for(int i = 0; i< array.length(); i++){
                                JSONObject object1 = array.getJSONObject(i);
                                int id = object1.getInt("id");
                                String ten = object1.getString("ten_loai");
                                String anh = object1.getString("anh_loai");
                                arrayList.add(new LoaiDanhMuc(
                                        id,anh, ten
                                ));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TimkiemTheoLoaiDanhMucActivity.this, "Lõi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "lỗi" + error);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idDM", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
