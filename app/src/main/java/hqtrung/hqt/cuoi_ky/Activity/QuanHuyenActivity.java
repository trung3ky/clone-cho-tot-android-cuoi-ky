package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import hqtrung.hqt.cuoi_ky.adapter.QuanHuyenActivityAdapter;
import hqtrung.hqt.cuoi_ky.adapter.TinhThanhActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class QuanHuyenActivity extends AppCompatActivity {
    RecyclerView recyclerViewQH;
    ImageView imgBack;
    ProgressDialog dialog;

    QuanHuyenActivityAdapter adapter;
    ArrayList<CacTinhThanh> arrayList;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_huyen);
        AnhXa();
    }

    private void AnhXa() {
        dialog = new ProgressDialog(this);
        imgBack = (ImageView) findViewById(R.id.ImageViewQH);
        recyclerViewQH = (RecyclerView) findViewById(R.id.RecyclerviewQH);

        Intent intent =  getIntent();
        id = intent.getStringExtra("id");
        String APIQuanHuyen = URL.ApiQuanHuyen;

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewQH.setLayoutManager(linearLayoutManager);

        adapter = new QuanHuyenActivityAdapter(this, arrayList);
        recyclerViewQH.setAdapter(adapter);

        QuanHuyen(APIQuanHuyen);
    }

    private void QuanHuyen(String apiQuanHuyen) {
        dialog.setTitle("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiQuanHuyen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
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
                        Toast.makeText(QuanHuyenActivity.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
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
