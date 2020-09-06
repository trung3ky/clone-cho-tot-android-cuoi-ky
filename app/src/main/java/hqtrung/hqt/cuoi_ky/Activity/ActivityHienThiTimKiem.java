package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.QuanHuyenActivityAdapter;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TinhThanhActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class ActivityHienThiTimKiem extends AppCompatActivity {

    TextView edttk;
    ImageButton ibtnBack;
    Button btnGia,btnDanhMuc;
    LinearLayout layoutnotfind;
    TextView txtDcTK;

    SanPhamAdapter adapter;
    ArrayList<SanPham> arrayList;
    RecyclerView recyclerView;

    String timkiem;
    String urlDanhSachTimKiem = URL.urlDanhSachTimKiem;

    public static String giamin = "";
    public static String giamax = "";

    public static String danhmuc = "";
    public static String loaidanhmuc = "";
    public static String AllDanhMuc = "";
    public static String AllLoaiDanhMuc = "";

    String tt = TinhThanhActivityAdapter.ATT.toString();
    String qh = QuanHuyenActivityAdapter.AQH.toString();
    public static String tq = "toanquoc";

    public static String ok = "";

    String urlTheoDanhMuc = URL.urlTheoDanhMuc;
    String urlTatCaLoaiDanhMuc = URL.urlTatCaLoaiDanhMuc;
    String urlNoiDungTheoGia = URL.urlNoiDungTheoGia;
    String urlDanhMucTheoGia = URL.urlDanhMucTheoGia;
    String urlLoaiDanhMucTheoGia = URL.urlLoaiDanhMucTheoGia;
    String urlTheoDiaChi = URL.urlTheoDiaChi;
    String urlTheoDiaChiDanhMuc = URL.urlTheoDiaChiDanhMuc;
    String urlTheoDiaChiLoaiDanhMuc = URL.urlTheoDiaChiLoaiDanhMuc;
    String urlTheoDiaChiLoaiDanhMucGia = URL.urlTheoDiaChiLoaiDanhMucGia;
    String urlTheoDiaChiGia = URL.urlTheoDiaChiGia;
    String urlTheoDiaChiDanhMucGia = URL.urlTheoDiaChiDanhMucGia;

    DecimalFormat formatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_tim_kiem);

        timkiem = Activity_Search.timkiem;

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");


        AnhXa();
    }

    private void AnhXa() {
        edttk = (TextView) findViewById(R.id.EditTextTimKiemHienThi);
        ibtnBack = (ImageButton) findViewById(R.id.TimKiemBackHienThi);
        btnGia = (Button) findViewById(R.id.ButtonGia);
        btnDanhMuc = (Button) findViewById(R.id.ButtonTheoDanhMuc);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerviewDanhSachTimKiem);
        layoutnotfind = (LinearLayout) findViewById(R.id.LayoutNotFind);
        txtDcTK = (TextView) findViewById(R.id.txtDiaChiTimKiem);

        arrayList = new ArrayList<>();

        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new SanPhamAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);



        edttk.setText(timkiem);

        edttk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHienThiTimKiem.this, Activity_Search.class));
            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok = "";
                TinhThanhActivityAdapter.ATT = "";
                QuanHuyenActivityAdapter.AQH = "";
                danhmuc ="";
                loaidanhmuc = "";
                AllDanhMuc = "";
                AllLoaiDanhMuc= "";
                giamax = "";
                giamin= "";
                tq = "toanquoc";
                startActivity(new Intent(ActivityHienThiTimKiem.this, MainActivity.class));
            }
        });

        btnDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHienThiTimKiem.this, TimKiemTheoDanhMucActivity.class));
            }
        });

        btnGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHienThiTimKiem.this, TImKiemTheoGiaActivity.class));
            }
        });

        txtDcTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok = "ok";
                Intent intent = new Intent(ActivityHienThiTimKiem.this, TinhThanhActivity.class);
                startActivity(intent);
            }
        });
        txtDcTK.setText("Toàn quốc");

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && danhmuc.isEmpty() && loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            SanPhamDiaChi(urlTheoDiaChi);
//            Toast.makeText(this, "ok theo điac chỉ", Toast.LENGTH_SHORT).show();
        }

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && !danhmuc.isEmpty() && loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            btnDanhMuc.setText(danhmuc);
            SanPhamTheoDiaChiDanhMuc(urlTheoDiaChiDanhMuc);
//            Toast.makeText(this, "ok theo đc danh muc", Toast.LENGTH_SHORT).show();
        }

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && !danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            SanPhamTheoDiaChiLoaiDanhMuc(urlTheoDiaChiLoaiDanhMuc);
//            Toast.makeText(this, "ok theo đc loai dm", Toast.LENGTH_SHORT).show();
        }

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && !danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamTheoDiaChiLoaiDanhMucGia(urlTheoDiaChiLoaiDanhMucGia);
//            Toast.makeText(this, "ok dc dm ldm gm gm", Toast.LENGTH_SHORT).show();
        }

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && danhmuc.isEmpty() && loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            btnDanhMuc.setText("Tất cả danh mục");
            btnGia.setText(giamin+" - "+giamax);
            SanPhamTheoDiaChiGia(urlTheoDiaChiGia);
//            Toast.makeText(this, "ok theo dc gm", Toast.LENGTH_SHORT).show();
        }

        if (!tt.equals("") && !qh.equals("") && tq.equals("") && !danhmuc.isEmpty() && loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText(tt+", "+qh);
            btnDanhMuc.setText(danhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamTheoDiaChiDanhMucGia(urlTheoDiaChiDanhMucGia);
//            Toast.makeText(this, "ok theo dc dm gm", Toast.LENGTH_SHORT).show();
        }

        if (!tq.equals("") && danhmuc.isEmpty() && loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            SanPhamTimKiem(urlDanhSachTimKiem);
        }

        if (!tq.equals("") && !danhmuc.isEmpty() && loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            btnDanhMuc.setText(danhmuc);
            SanPhamTatCaLoaiDanhMuc(urlTatCaLoaiDanhMuc);
        }
        if (!tq.equals("") && !danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            SanPhamDanhMuc(urlTheoDanhMuc);
        }
        if (!tq.equals("") && !danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamLoaiDanhMucTheoGia(urlLoaiDanhMucTheoGia);
        }
        if (!tq.equals("") && danhmuc.isEmpty() && loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            btnGia.setText(giamin+" - "+giamax);
            SanPhamNoiDungTheoGia(urlNoiDungTheoGia);
        }
        if (!tq.equals("") && !danhmuc.isEmpty() && loaidanhmuc.isEmpty() && !giamin.isEmpty() && !giamax.isEmpty()){
            txtDcTK.setText("Toàn quốc");
            btnDanhMuc.setText(danhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamDanhMucTheoGia(urlDanhMucTheoGia);
        }



        if (!danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            SanPhamDanhMuc(urlTheoDanhMuc);
        }

        if (!AllDanhMuc.isEmpty() && giamax.isEmpty() && giamin.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnDanhMuc.setText("Tất cả danh mục");
        }
        if (!AllLoaiDanhMuc.isEmpty() && giamin.isEmpty() && giamax.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnDanhMuc.setText(danhmuc);
            SanPhamTatCaLoaiDanhMuc(urlTatCaLoaiDanhMuc);
        }
        if (!giamin.isEmpty() && !giamax.isEmpty() && danhmuc.isEmpty() && loaidanhmuc.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnGia.setText(giamin+" - "+giamax);
            SanPhamNoiDungTheoGia(urlNoiDungTheoGia);
        }
        if (!giamin.isEmpty() && !giamax.isEmpty() && !danhmuc.isEmpty() && !loaidanhmuc.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnDanhMuc.setText(danhmuc+" - "+loaidanhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamLoaiDanhMucTheoGia(urlLoaiDanhMucTheoGia);
        }
        if (!giamin.isEmpty() && !giamax.isEmpty() && !danhmuc.isEmpty() && loaidanhmuc.isEmpty() && tt.isEmpty() && qh.isEmpty() && tq.isEmpty()){
            btnDanhMuc.setText(danhmuc);
            btnGia.setText(giamin+" - "+giamax);
            SanPhamDanhMucTheoGia(urlDanhMucTheoGia);
        }


    }

    private void SanPhamTheoDiaChiDanhMucGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTheoDiaChiGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTheoDiaChiLoaiDanhMucGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                param.put("loaidanhmuc", loaidanhmuc);
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTheoDiaChiLoaiDanhMuc(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                param.put("loaidanhmuc", loaidanhmuc);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTheoDiaChiDanhMuc(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamDiaChi(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("tinhthanh", tt);
                param.put("quanhuyen", qh);
                param.put("noidung", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamLoaiDanhMucTheoGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                param.put("loaidanhmuc", loaidanhmuc);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamDanhMucTheoGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                param.put("noidung", timkiem);
                param.put("danhmuc", danhmuc);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamNoiDungTheoGia(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("giamin", giamin);
                param.put("giamax", giamax);
                param.put("noidung", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTatCaLoaiDanhMuc(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("danhmuc", danhmuc);
                param.put("noidung", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamDanhMuc(String url){
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("danhmuc", danhmuc);
                param.put("loaidanhmuc", loaidanhmuc);
                param.put("noidung", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SanPhamTimKiem(String url) {
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
                                adapter.notifyDataSetChanged();
                            }else{
                                recyclerView.setVisibility(View.GONE);
                                layoutnotfind.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivityHienThiTimKiem.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("noidung", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
