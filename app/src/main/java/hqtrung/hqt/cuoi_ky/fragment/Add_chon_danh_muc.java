package hqtrung.hqt.cuoi_ky.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import hqtrung.hqt.cuoi_ky.Activity.AuthActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class Add_chon_danh_muc extends Fragment {
    RecyclerView recyclerViewAddDM;
    ArrayList<DanhMuc> danhMucArrayList;
    AddDanhMucAdapter adapter;
    LinearLayout layout;
    TextView txtchon;

    ImageView imgBack;

    String url = URL.urlAddDanhMuc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_danh_muc, container, false);
        imgBack = (ImageView) view.findViewById(R.id.ImageViewAddDM);
        layout = (LinearLayout) view.findViewById(R.id.layoutchon);
        txtchon = (TextView) view.findViewById(R.id.TextViewDaChon);

        if (!AddDanhMucAdapter.tenDM.equals("")){
            layout.setVisibility(View.VISIBLE);
            txtchon.setText(AddDanhMucAdapter.tenDM);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AddDanhMucAdapter.tenDM.equals("")){
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn hủy đăng tin không?");
                    builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    });
                    builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.show();
                }else{
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        });

        String user = MainActivity.preferences.getString("user_name", "");
        if (user.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Thông Báo");
            builder.setMessage("Bạn cần đăng nhập tài khoản!");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), AuthActivity.class));
                }
            });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            });
            builder.show();
        }else{
            recyclerViewAddDM = (RecyclerView) view.findViewById(R.id.RecyclerviewAddDanhMuc);
            danhMucArrayList = new ArrayList<>();

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            recyclerViewAddDM.setLayoutManager(linearLayoutManager);

            adapter= new AddDanhMucAdapter(getActivity(), danhMucArrayList);
            recyclerViewAddDM.setAdapter(adapter);

            addDM(url);
        }

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.addToBackStack("aaa");
//        fragmentTransaction.commit();




        return view;
    }

    private void addDM(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObjectt = response.getJSONObject(i);
                                danhMucArrayList.add(new DanhMuc(
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
                Toast.makeText(getActivity(), "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi"+error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


}
