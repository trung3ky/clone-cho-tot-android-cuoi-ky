package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddTinhThanhAdapter;
import hqtrung.hqt.cuoi_ky.adapter.LoaiAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TinhThanhActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class TinhThanhActivity extends AppCompatActivity {
    TextView txtALl;
    ImageView imgBack;
    RecyclerView recyclerViewTT;
    String APITinhThanh = URL.ApiTinhThanh;
    ProgressDialog dialog;
//
//    public static SharedPreferences preferences;
//    public static SharedPreferences.Editor editor;
    String tq = "toanquoc";
    public  static  String kt;

    TinhThanhActivityAdapter adapter;
    ArrayList<CacTinhThanh> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinh_thanh);
        AnhXa();
    }

    private void AnhXa() {
        dialog = new ProgressDialog(this);
        txtALl = (TextView) findViewById(R.id.TextViewAll);
        imgBack = (ImageView) findViewById(R.id.ImageViewTT);
        recyclerViewTT = (RecyclerView) findViewById(R.id.RecyclerviewTT);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityHienThiTimKiem.ok.equals("ok")){
                    ActivityHienThiTimKiem.tq = "toanquoc";
                    Intent intent = new Intent(TinhThanhActivity.this, ActivityHienThiTimKiem.class);
                    startActivity(intent);
                }else {
                    Activity_SanPham.editor = Activity_SanPham.preferences.edit();
                    Activity_SanPham.editor.putString("tq", tq);
                    Activity_SanPham.editor.apply();

                    startActivity(new Intent(TinhThanhActivity.this, Activity_SanPham.class));
                }
            }
        });

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewTT.setLayoutManager(linearLayoutManager);

        adapter = new TinhThanhActivityAdapter(this, arrayList);
        recyclerViewTT.setAdapter(adapter);

        TinhThanh(APITinhThanh);

    }

    private void TinhThanh(String url) {
        dialog.setTitle("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
//                            JSONObject object = new JSONObject(response);
//                            JSONArray array = object.getJSONArray("LtsItem");
                            JSONArray array = new JSONArray(response);
                            for(int i = 0; i< array.length(); i++){
                                JSONObject object1 = array.getJSONObject(i);
                                int id = object1.getInt("id");
                                String ten = object1.getString("name");
                                arrayList.add(new CacTinhThanh(
                                        ten, id
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
                        Toast.makeText(TinhThanhActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "lỗi" + error);
                    }
                });
        requestQueue.add(stringRequest);
    }
}
