package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import hqtrung.hqt.cuoi_ky.adapter.SanPhamTheoLoaiAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TinhThanhActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class SanPhamTheoLoaiActivity extends AppCompatActivity {
    ImageView imgBack;
    TextView txtDiaChi, txtDm, txtLDM;
    EditText edtTKTL;
    public static LinearLayout txtTB;
    LinearLayout layout;
    RecyclerView recyclerViewSPtheoLoai;
    ArrayList<SanPham> arrayList;
    SanPhamTheoLoaiAdapter adapter;

    String urlSP = URL.urlSPTheoLoai;
    String urlSPL = URL.urlSPLoai;
    String idLoai;

    String tt = TinhThanhActivityAdapter.ATT.toString();
    String qh = QuanHuyenActivityAdapter.AQH.toString();
    String tq;

    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_theo_loai);

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");

        AnhXa();

        tq = Activity_SanPham.preferences.getString("tq", "");

        Intent intent = getIntent();
        idLoai = intent.getStringExtra("idLoai");



        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSPtheoLoai.setLayoutManager(linearLayoutManager1);

        adapter = new SanPhamTheoLoaiAdapter(this, arrayList);
        recyclerViewSPtheoLoai.setAdapter(adapter);

        if (!tt.equals("") && !qh.equals("") && tq.equals("")){
            txtDiaChi.setText(tt+", "+qh);
            AddSPTheoLoai(urlSPL);
        }
        if (!tq.equals("")){
            txtDiaChi.setText("Toàn quốc");
            Add(urlSP);
        }

    }

    private void AddSPTheoLoai(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("khongco") == false){
                                JSONArray array = jsonObject.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    int number = object.getInt("gia");
                                    String gia = formatter.format(number);
                                    arrayList.add(new SanPham(
                                            object.getString("id"),
                                            object.getString("tieu_de"),
                                            gia,
                                            object.getString("hinh_anh"),
                                            true
                                    ));
                                }
                                adapter.notifyDataSetChanged();
                                JSONArray arrayDM = jsonObject.getJSONArray("danhmuc");
                                for (int j = 0 ; j < arrayDM.length(); j++){
                                    JSONObject objectDM = arrayDM.getJSONObject(j);
                                    txtDm.setText(objectDM.getString("ten_danh_muc"));
                                }
                                JSONArray arrayLDM = jsonObject.getJSONArray("loaidanhmuc");
                                for (int t = 0 ; t < arrayDM.length(); t++){
                                    JSONObject objectLDM = arrayLDM.getJSONObject(t);
                                    txtLDM.setText(objectLDM.getString("ten_loai"));
                                }
                            }else{
                                JSONArray arrayDM = jsonObject.getJSONArray("danhmuc");
                                for (int j = 0 ; j < arrayDM.length(); j++){
                                    JSONObject objectDM = arrayDM.getJSONObject(j);
                                    txtDm.setText(objectDM.getString("ten_danh_muc"));
                                }
                                JSONArray arrayLDM = jsonObject.getJSONArray("loaidanhmuc");
                                for (int t = 0 ; t < arrayDM.length(); t++){
                                    JSONObject objectLDM = arrayLDM.getJSONObject(t);
                                    txtLDM.setText(objectLDM.getString("ten_loai"));
                                }
                                txtTB.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SanPhamTheoLoaiActivity.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("loai", idLoai);
                param.put("tt", tt);
                param.put("qh", qh);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Add(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true) {
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
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
                                JSONArray arrayDM = object.getJSONArray("danhmuc");
                                for (int j = 0 ; j < arrayDM.length(); j++){
                                    JSONObject objectDM = arrayDM.getJSONObject(j);
                                    txtDm.setText(objectDM.getString("ten_danh_muc"));
                                }
                                JSONArray arrayLDM = object.getJSONArray("loaidanhmuc");
                                for (int t = 0 ; t < arrayDM.length(); t++){
                                    JSONObject objectLDM = arrayLDM.getJSONObject(t);
                                    txtLDM.setText(objectLDM.getString("ten_loai"));
                                }
                            }else{
                                JSONArray arrayDM = object.getJSONArray("danhmuc");
                                for (int j = 0 ; j < arrayDM.length(); j++){
                                    JSONObject objectDM = arrayDM.getJSONObject(j);
                                    txtDm.setText(objectDM.getString("ten_danh_muc"));
                                }
                                JSONArray arrayLDM = object.getJSONArray("loaidanhmuc");
                                for (int t = 0 ; t < arrayDM.length(); t++){
                                    JSONObject objectLDM = arrayLDM.getJSONObject(t);
                                    txtLDM.setText(objectLDM.getString("ten_loai"));
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
                        Toast.makeText(SanPhamTheoLoaiActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idLoai", idLoai);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        txtTB = (LinearLayout) findViewById(R.id.TextViewThongBaoLoai);
        txtDm = (TextView) findViewById(R.id.LocDanhMuc);
        txtLDM = (TextView) findViewById(R.id.LocLoaiDanhMuc);
        imgBack = (ImageView) findViewById(R.id.imageButtonBackSanPhamTheoLoai);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiSPtheoLoai);
        edtTKTL = (EditText) findViewById(R.id.edittextTimKiemSPTheoLoai);
        recyclerViewSPtheoLoai = (RecyclerView) findViewById(R.id.RecyclerviewSanPhamTheoLoai);



        txtDm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamTheoLoaiActivity.this, Activity_SanPham.class));
            }
        });
        txtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SanPhamTheoLoaiActivity.this, TinhThanhActivity.class));
            }
        });

        edtTKTL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String Text){
        ArrayList<SanPham> filterlist = new ArrayList<>();

        for (SanPham item : arrayList){
            if (item.getNameSP().toLowerCase().contains(Text.toLowerCase())){
                filterlist.add(item);
            }
        }
        adapter.filterList(filterlist);
    }
}
