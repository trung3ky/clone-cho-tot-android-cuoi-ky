package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import hqtrung.hqt.cuoi_ky.adapter.LichSuTimKiemAdapter;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Home;
import hqtrung.hqt.cuoi_ky.model.TimKiem;

public class Activity_Search extends AppCompatActivity {
    ImageView imgBack;
    EditText edtTimKiem;
    TextView txtXoa;
    public static String timkiem;
    RelativeLayout layout;

    RecyclerView recyclerviewLichSuTimKiem;
    LichSuTimKiemAdapter adapter;
    ArrayList<TimKiem> arrayList;

    String urlXoa = URL.urlXoaLichSu;
    String urlTimKiem = URL.urlTimKiem;
    String urlLichSu = URL.urlLichsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__search);
        AnhXa();
    }

    private void AnhXa() {
        imgBack = findViewById(R.id.TimKiemBack);
        edtTimKiem = findViewById(R.id.EditTextTimKiem);
        recyclerviewLichSuTimKiem = findViewById(R.id.RecyclerviewLichSuTimKiem);
        layout = (RelativeLayout) findViewById(R.id.layoutlichsu);
        txtXoa =(TextView) findViewById(R.id.xoaLichSu);

        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerviewLichSuTimKiem.setLayoutManager(linearLayoutManager);

        adapter = new LichSuTimKiemAdapter(this, arrayList);
        recyclerviewLichSuTimKiem.setAdapter(adapter);

        GetLichSu(urlLichSu);

        // hiển thị bàn phím
        edtTimKiem.requestFocus();
        InputMethodManager imgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.showSoftInput(edtTimKiem, 0);


        // trong xml thêm
        //       android:imeOptions="actionSearch"
        //       android:inputType="text"
        //       android:maxLines="1"
        edtTimKiem.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    timkiem = edtTimKiem.getText().toString();
                    if (edtTimKiem.getText().length() > 1){
                        TimKiem(urlTimKiem);
                    }
                    return true;
                }
                return false;
            }
        });

        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtTimKiem.getText().length() == 0 ){
                    recyclerviewLichSuTimKiem.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.VISIBLE);
                }else{
                    recyclerviewLichSuTimKiem.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        txtXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaLichSu(urlXoa);

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(Activity_Search.this, MainActivity.class));
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
            }
        });
    }

    private void XoaLichSu(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arrayList.clear();
                        GetLichSu(urlLichSu);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Search.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void GetLichSu(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest  = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("thanhcong") == true){
                                JSONArray array = response.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    arrayList.add(new TimKiem(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("noi_dung")
                                    ));
                                }
                                String count = response.getString("count");
                                if (count.equals("0") ){
                                    layout.setVisibility(View.GONE);
                                }
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
                        Toast.makeText(Activity_Search.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    private void TimKiem(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                Intent intent = new Intent(Activity_Search.this, ActivityHienThiTimKiem.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Activity_Search.this, "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_Search.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("timkiem", timkiem);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
