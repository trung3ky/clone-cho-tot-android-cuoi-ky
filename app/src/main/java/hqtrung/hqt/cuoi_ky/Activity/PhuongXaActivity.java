package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
import hqtrung.hqt.cuoi_ky.adapter.PhuongXaActivityAdapter;
import hqtrung.hqt.cuoi_ky.adapter.QuanHuyenActivityAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class PhuongXaActivity extends AppCompatActivity {

    RecyclerView recyclerViewPX;
    ImageView imgBack;
    ProgressDialog dialog;

    PhuongXaActivityAdapter adapter;
    ArrayList<CacTinhThanh> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phuong_xa);
        AnhXa();
    }

    private void AnhXa() {
        dialog = new ProgressDialog(this);
        recyclerViewPX = (RecyclerView) findViewById(R.id.RecyclerviewPX);
        imgBack = (ImageView) findViewById(R.id.ImageViewPX);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent= getIntent();
        String id1 = intent.getStringExtra("id1");
        String APIPhuongXa = "https://thongtindoanhnghiep.co/api/district/"+id1+"/ward";

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewPX.setLayoutManager(linearLayoutManager);

        adapter = new PhuongXaActivityAdapter(this, arrayList);
        recyclerViewPX.setAdapter(adapter);



        PhuongXa(APIPhuongXa);
    }
    private void PhuongXa(String url){
        dialog.setTitle("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dialog.dismiss();
                        for(int i = 0 ; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String ten = object.getString("Title");
                                int id = object.getInt("ID");
                                arrayList.add(new CacTinhThanh(
                                        ten, id
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}
