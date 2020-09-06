package hqtrung.hqt.cuoi_ky.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.Chat_Activity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.QuanLyTinAdapter;
import hqtrung.hqt.cuoi_ky.adapter.QuanlyTinChoAdapter;
import hqtrung.hqt.cuoi_ky.adapter.QuanlyTinDuyetAdapter;
import hqtrung.hqt.cuoi_ky.adapter.YeuThichAdapter;
import hqtrung.hqt.cuoi_ky.model.QuanLyTin;
import hqtrung.hqt.cuoi_ky.model.SanPham;
import hqtrung.hqt.cuoi_ky.model.TinTuChoi;

public class Activity_Sale extends Fragment {
    View view;
    RecyclerView recyclerViewQLT, recyclerViewQLTD, recyclerViewQLC;
    ImageView imgChat;

    QuanLyTinAdapter adapter;
    QuanlyTinDuyetAdapter adapterduyet;
    QuanlyTinChoAdapter adaptercho;

    ArrayList<QuanLyTin> arrayList;
    ArrayList<QuanLyTin> arrayListDuyet;
    ArrayList<TinTuChoi> arrayListCho;

    TextView txtCountDB, txtDB;
    TextView txtCountDuyet, txtDuyet, txtnotFind, txtTuChoi,  txtCountTC;

    String iduser;
    String urlQLT = URL.urlQLT;
    String urlQLTD = URL.urlQLTD;
    String urlQLC = URL.urlTinCho;

    DecimalFormat formatter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_sale, container, false);
        iduser = MainActivity.preferences.getString("id", "");

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        AnhXa();

        txtDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewQLT.setVisibility(View.VISIBLE);
                recyclerViewQLTD.setVisibility(View.GONE);
                recyclerViewQLC.setVisibility(View.GONE);
                txtDuyet.setTextColor(Color.GRAY);
                txtCountDuyet.setTextColor(Color.GRAY);
                txtTuChoi.setTextColor(Color.GRAY);
                txtCountTC.setTextColor(Color.GRAY);
                txtDB.setTextColor(Color.BLACK);
                txtCountDB.setTextColor(Color.BLACK);
            }
        });
        txtDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewQLTD.setVisibility(View.VISIBLE);
                recyclerViewQLT.setVisibility(View.GONE);
                recyclerViewQLC.setVisibility(View.GONE);
                txtDB.setTextColor(Color.GRAY);
                txtCountDB.setTextColor(Color.GRAY);
                txtDuyet.setTextColor(Color.BLACK);
                txtCountDuyet.setTextColor(Color.BLACK);
                txtTuChoi.setTextColor(Color.GRAY);
                txtCountTC.setTextColor(Color.GRAY);
            }
        });
        txtTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewQLTD.setVisibility(View.GONE);
                recyclerViewQLT.setVisibility(View.GONE);
                recyclerViewQLC.setVisibility(View.VISIBLE);
                txtDB.setTextColor(Color.GRAY);
                txtCountDB.setTextColor(Color.GRAY);
                txtDuyet.setTextColor(Color.GRAY);
                txtCountDuyet.setTextColor(Color.GRAY);
                txtTuChoi.setTextColor(Color.BLACK);
                txtCountTC.setTextColor(Color.BLACK);
            }
        });
        return view;
    }

    private void AnhXa() {
        imgChat = (ImageView) view.findViewById(R.id.Chat) ;
        txtCountDuyet = (TextView) view.findViewById(R.id.countDuyet);
        txtDuyet = (TextView) view.findViewById(R.id.DangDoiDuyet);
        txtnotFind = (TextView) view.findViewById(R.id.txtktimthay);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Chat_Activity.class));
            }
        });

        txtTuChoi = (TextView) view.findViewById(R.id.BiTuChoi);
        txtCountTC = (TextView) view.findViewById(R.id.countTuChoi);

        txtDB = (TextView) view.findViewById(R.id.DangBan);
        txtCountDB = (TextView) view.findViewById(R.id.countDB);
        recyclerViewQLTD = (RecyclerView) view.findViewById(R.id.RecyclerviewQuanLyTinDuyet);
        recyclerViewQLT = (RecyclerView) view.findViewById(R.id.RecyclerviewQuanLyTin);
        recyclerViewQLC = (RecyclerView) view.findViewById(R.id.RecyclerviewQuanLyTinCho);

        arrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewQLT.setLayoutManager(linearLayoutManager);

        adapter = new QuanLyTinAdapter(getActivity(), arrayList);
        recyclerViewQLT.setAdapter(adapter);


        //duyet
        arrayListDuyet = new ArrayList<>();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewQLTD.setLayoutManager(linearLayoutManager1);

        adapterduyet = new QuanlyTinDuyetAdapter(getActivity(), arrayListDuyet);
        recyclerViewQLTD.setAdapter(adapterduyet);

        arrayListCho = new ArrayList<>();
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewQLC.setLayoutManager(linearLayoutManager2);

        adaptercho = new QuanlyTinChoAdapter(getActivity(), arrayListCho);
        recyclerViewQLC.setAdapter(adaptercho);



        QuanLyTin(urlQLT);
        QuanLyTinDuyet(urlQLTD);
        QuanLyTinCho(urlQLC);
    }

    private void QuanLyTin(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("thanhcong") == true){
                                JSONArray array = jsonObject.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    int number = object.getInt("gia");
                                    String gia = formatter.format(number);
                                    arrayList.add(new QuanLyTin(
                                            object.getString("id"),
                                            object.getString("tieu_de"),
                                            gia,
                                            object.getString("hinh_anh"),
                                            object.getString("date"),
                                            true
                                    ));
                                }
                                String count = jsonObject.getString("count");
                                txtCountDB.setText("("+count+")");
                                txtCountDB.setVisibility(View.VISIBLE);
                                txtDB.setTextColor(Color.BLACK);
                                txtCountDB.setTextColor(Color.BLACK);
                                adapter.notifyDataSetChanged();
                                txtnotFind.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", iduser);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void QuanLyTinCho(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("thanhcong") == true){
                                JSONArray array = jsonObject.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    int number = object.getInt("gia");
                                    String gia = formatter.format(number);
                                    arrayListCho.add(new TinTuChoi(
                                            object.getString("id"),
                                            object.getString("tieu_de"),
                                            gia,
                                            object.getString("hinh_anh"),
                                            object.getString("date"),
                                            object.getString("noi_dung_tu_choi"),
                                            true
                                    ));
                                }
                                String count = jsonObject.getString("count");
                                txtCountTC.setText("("+count+")");
                                txtCountTC.setVisibility(View.VISIBLE);
                                adaptercho.notifyDataSetChanged();
                                txtnotFind.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", iduser);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void QuanLyTinDuyet(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("thanhcong") == true){
                                JSONArray array = jsonObject.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject object = array.getJSONObject(i);
                                    int number = object.getInt("gia");
                                    String gia = formatter.format(number);
                                    arrayListDuyet.add(new QuanLyTin(
                                            object.getString("id"),
                                            object.getString("tieu_de"),
                                            gia,
                                            object.getString("hinh_anh"),
                                            object.getString("date"),
                                            true
                                    ));
                                }
                                String count = jsonObject.getString("count");
                                txtCountDuyet.setText("("+count+")");
                                txtCountDuyet.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                                txtnotFind.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", iduser);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
