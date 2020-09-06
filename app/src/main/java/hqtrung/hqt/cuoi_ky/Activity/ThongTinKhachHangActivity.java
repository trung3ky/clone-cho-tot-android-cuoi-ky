package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    TextView txtTitle, txtNme,txtNgay, txtDC, txtsotin;
    ImageView imgAnh, imgBack;
    RecyclerView recyclerView;
    ArrayList<SanPham> arrayList;
    SanPhamAdapter adapter;

    String id;
    String urlUser = URL.urlUser;
    String urlSanPhamBan = URL.urlSanPhamBan;
    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        AnhXa();
        GetUser(urlUser);
    }

    private void AnhXa() {
        txtTitle = (TextView) findViewById(R.id.TenNguoiDung);
        txtNme = (TextView) findViewById(R.id.TextVIewName);
        imgAnh = (ImageView) findViewById(R.id.HinhAnhUser1);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerviewSanPhamCuaND);
        txtNgay = (TextView) findViewById(R.id.NgayThamGia);
        txtDC = (TextView) findViewById(R.id.DiaChi);
        imgBack= (ImageView) findViewById(R.id.ImageViewBack);
        txtsotin = (TextView) findViewById(R.id.sotin);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SanPhamAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

        SanPhamCuaNguoiDung(urlSanPhamBan);
    }

    private void SanPhamCuaNguoiDung(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                JSONArray array = object.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    int number = jsonObject.getInt("gia");
                                    String gia = formatter.format(number);

                                    arrayList.add(new SanPham(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("tieu_de"),
                                            gia,
                                            jsonObject.getString("hinh_anh"),
                                            true

                                    ));
                                }
                                String count = object.getString("count");
                                txtsotin.setText("- "+count+" tin");
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThongTinKhachHangActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", id);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetUser(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                JSONArray array = object.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    txtTitle.setText(jsonObject.getString("user_name"));
                                    txtNme.setText(jsonObject.getString("user_name"));
                                    String anh = URL.urlAnh+jsonObject.getString("hinh_anh_user");
                                    Picasso.get().load(anh).into(imgAnh);
                                    txtNgay.setText(jsonObject.getString("date"));
                                    String diachi = jsonObject.getString("diachi");
                                    if (!diachi.equals("")){
                                        txtDC.setText(diachi);
                                    }else {
                                        txtDC.setText("chưa có địa chỉ");
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThongTinKhachHangActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", id);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
