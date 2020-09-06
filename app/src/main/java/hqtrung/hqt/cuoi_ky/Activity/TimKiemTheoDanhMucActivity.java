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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.DanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TimKiemTheoDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class TimKiemTheoDanhMucActivity extends AppCompatActivity {
    RecyclerView recyclerViewDanhMuc;
    ImageView imgClose;
    RelativeLayout layoutAll;

    TimKiemTheoDanhMucAdapter adapter;
    ArrayList<DanhMuc> arrayList;

    String url = URL.urlAddDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_theo_danh_muc);

        AnhXa();
    }

    private void AnhXa(){
        imgClose = (ImageView) findViewById(R.id.close);
        layoutAll = (RelativeLayout) findViewById(R.id.layouttatca);
        recyclerViewDanhMuc = (RecyclerView) findViewById(R.id.RecyclerviewTheoDanhMuc);

        layoutAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.AllDanhMuc = "tatca";
                ActivityHienThiTimKiem.AllLoaiDanhMuc = "";
                ActivityHienThiTimKiem.danhmuc = "";
                ActivityHienThiTimKiem.loaidanhmuc = "";
                startActivity(new Intent(TimKiemTheoDanhMucActivity.this, ActivityHienThiTimKiem.class));
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimKiemTheoDanhMucActivity.this, ActivityHienThiTimKiem.class));
            }
        });


        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewDanhMuc.setLayoutManager(linearLayoutManager);

        adapter = new TimKiemTheoDanhMucAdapter(this, arrayList);
        recyclerViewDanhMuc.setAdapter(adapter);

        addDM(url);

    }

    private void addDM(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObjectt = response.getJSONObject(i);
                                arrayList.add(new DanhMuc(
                                        jsonObjectt.getInt("id"),
                                        jsonObjectt.getString("anh"),
                                        jsonObjectt.getString("ten_danh_muc")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TimKiemTheoDanhMucActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi"+error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
