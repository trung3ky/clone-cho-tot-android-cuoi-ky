package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
import hqtrung.hqt.cuoi_ky.adapter.UpdateDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class UpdateDanhMuc extends AppCompatActivity {
    RecyclerView recyclerView;
    UpdateDanhMucAdapter adapter;
    ArrayList<DanhMuc> arrayList;

    String url = URL.urlAddDanhMuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_danh_muc);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerviewAddDanhMuc1);
        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter= new UpdateDanhMucAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

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
                Toast.makeText(UpdateDanhMuc.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi"+error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
