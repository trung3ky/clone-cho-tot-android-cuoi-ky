package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.LoaiAdapter;
import hqtrung.hqt.cuoi_ky.adapter.PhuongXaActivityAdapter;
import hqtrung.hqt.cuoi_ky.adapter.QuanHuyenActivityAdapter;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TinhThanhActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class Activity_SanPham extends AppCompatActivity {
    public static LinearLayout txtTB;
    EditText edtTKSP;
    ImageButton ibtnBack;
    ProgressDialog dialog;
    TextView txtDiaChi;

    RecyclerView recyclerView;
    ArrayList<LoaiDanhMuc> loaiAdapterArrayList;
    LoaiAdapter loaiAdapter;


    RecyclerView recyclerViewSP;
    ArrayList<SanPham> sanPhamArrayList;
    SanPhamAdapter SPadapter;

    String urlLoai = URL.urlSanPham;
    String urlSPDC = URL.urlSPTheoDiaChi;
    String id ;
    String searchName;
    String searchName2;
    String id2;

    String tt = TinhThanhActivityAdapter.ATT.toString();
    String qh = QuanHuyenActivityAdapter.AQH.toString();


    String urlSP = URL.urlShowSanPham;

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    String tq;

    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__san_pham);
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");

        AnhXa();

        Intent intent = getIntent();
        searchName = intent.getStringExtra("SearchName");
        id = intent.getStringExtra("id");
//        id1 = intent.getStringExtra("id");

        preferences = getSharedPreferences("idDanhMuc", MODE_PRIVATE);
        editor = preferences.edit();

        if (id== null){
            id = Activity_SanPham.preferences.getString("idDM","");
            searchName = Activity_SanPham.preferences.getString("searchName","");
            tq = Activity_SanPham.preferences.getString("tq","");
        }else {
            editor.putString("idDM", id);
            editor.putString("searchName", searchName);
            editor.putString("tq", "toanquoc");
            editor.apply();
        }
        id2 = Activity_SanPham.preferences.getString("idDM", "");
        searchName2 = Activity_SanPham.preferences.getString("searchName", "");
        tq = Activity_SanPham.preferences.getString("tq", "");

        edtTKSP.setHint(searchName2);
        //nút back về
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_SanPham.this, MainActivity.class));
            }
        });

        //recyclerview loai danh muc
        loaiAdapterArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loaiAdapter = new LoaiAdapter(this, loaiAdapterArrayList);
        recyclerView.setAdapter(loaiAdapter);

        //---------
        GetUrlLoai(urlLoai);


        //recycler view san phẩm
        recyclerViewSP = (RecyclerView) findViewById(R.id
                .RecyclerviewSanPham);
        sanPhamArrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewSP.setLayoutManager(linearLayoutManager1);

        SPadapter = new SanPhamAdapter(this, sanPhamArrayList);
        recyclerViewSP.setAdapter(SPadapter);

        if (!tt.equals("") && !qh.equals("") && tq.equals("")){
            txtDiaChi.setText(tt+", "+qh);
            AddSPTheoDC(urlSPDC);
        }
        if (!tq.equals("")){
            txtDiaChi.setText("Toàn quốc");
            AddSP(urlSP);
        }

    }


    private void AddSPTheoDC(String url){
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
                                        sanPhamArrayList.add(new SanPham(
                                                object.getString("id"),
                                                object.getString("tieu_de"),
                                                gia,
                                                object.getString("hinh_anh"),
                                                true
                                        ));
                                    }
                                    SPadapter.notifyDataSetChanged();
                                }else{
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
                            Toast.makeText(Activity_SanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("idDM", id2);
                    param.put("tt", tt);
                    param.put("qh", qh);
                    return param;
                }
            };
            requestQueue.add(stringRequest);
    }

    private void GetUrlLoai(String url) {
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
                                loaiAdapterArrayList.add(new LoaiDanhMuc(
                                        id,anh, ten
                                ));
                            }
                            loaiAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_SanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param  =new HashMap<>();
                param.put("idDM", id2);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AddSP(String url) {
        dialog.setTitle("thông báo");
        dialog.setMessage("đang load, vui lòng đợi");
        dialog.show();
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                JSONArray array = object.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject jsonObject = array.getJSONObject(i);

                                    int number = jsonObject.getInt("gia");
                                    String gia = formatter.format(number);

                                    sanPhamArrayList.add(new SanPham(
                                            jsonObject.getString("id"),
                                            jsonObject.getString("tieu_de"),
                                            gia,
                                            jsonObject.getString("hinh_anh"),
                                            true

                                    ));
                                }
                                SPadapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_SanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("idDanhMuc",id2);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        edtTKSP = (EditText) findViewById(R.id.edittextTimKiemSP);
        ibtnBack = (ImageButton) findViewById(R.id.imageButtonBack);
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerviewLoai);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtTB = (LinearLayout) findViewById(R.id.TextViewThongBao);
        dialog = new ProgressDialog(this);

        edtTKSP.addTextChangedListener(new TextWatcher() {
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


        txtDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_SanPham.this, TinhThanhActivity.class);
                startActivity(intent);

            }
        });
    }

    private void filter(String Text){
        ArrayList<SanPham> filterlist = new ArrayList<>();

        for (SanPham item : sanPhamArrayList){
            if (item.getNameSP().toLowerCase().contains(Text.toLowerCase())){
                filterlist.add(item);
            }
        }
        SPadapter.filterList(filterlist);
    }
}
