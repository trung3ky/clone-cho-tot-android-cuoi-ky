package hqtrung.hqt.cuoi_ky.fragment;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddLoaiDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class Add_loai_danh_muc extends Fragment {
    RecyclerView recyclerViewLoaiDm;
    AddLoaiDanhMucAdapter adapterLDM;
    ArrayList<LoaiDanhMuc> arrayList;

    String UrlLoaiDM = URL.urlSanPham;
    ImageView imgLDM;

    LinearLayout layoutLDM;
    TextView txtLDM;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_loai_danh_muc, container, false);
        AnhXa();




        arrayList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewLoaiDm.setLayoutManager(linearLayoutManager);

        adapterLDM = new AddLoaiDanhMucAdapter(getActivity(), arrayList);
        recyclerViewLoaiDm.setAdapter(adapterLDM);

        AddLDM(UrlLoaiDM);

        return view;
    }
    private void AddLDM(String url){
//        Bundle bundle = getArguments();
//        if (bundle != null){
//            int  id  = bundle.getInt("id");
        if (!AddDanhMucAdapter.id.equals("")){
            String id = AddDanhMucAdapter.id;
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                                    arrayList.add(new LoaiDanhMuc(
                                            id,anh, ten
                                    ));
                                }
                                adapterLDM.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Lõi hệ thống", Toast.LENGTH_SHORT).show();
                            Log.d("AAA", "lỗi" + error);
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("idDM", String.valueOf(id));
                    return param;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void AnhXa(){
        recyclerViewLoaiDm = (RecyclerView) view.findViewById(R.id.RecyclerviewAddLoaiDanhMuc);
        imgLDM = (ImageView) view.findViewById(R.id.ImageViewAddLDM);
        layoutLDM = (LinearLayout) view.findViewById(R.id.layoutchonLDM);
        txtLDM = (TextView) view.findViewById(R.id.TextViewDaChonLDM);

        if (!AddLoaiDanhMucAdapter.tenLDM.equals("")){
            layoutLDM.setVisibility(View.VISIBLE);
            txtLDM.setText(AddLoaiDanhMucAdapter.tenLDM);
        }

        imgLDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLoaiDanhMucAdapter.tenLDM ="";
                Fragment fragment = new Add_chon_danh_muc();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });
    }
}
