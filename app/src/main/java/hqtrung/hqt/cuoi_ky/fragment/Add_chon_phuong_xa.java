package hqtrung.hqt.cuoi_ky.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import hqtrung.hqt.cuoi_ky.adapter.AddPhuongXaAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddQuanHuyenAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class Add_chon_phuong_xa extends Fragment {
    ProgressDialog dialog;

    RecyclerView recyclerViewPX;
    AddPhuongXaAdapter addPhuongXaAdapterPX;
    ArrayList<CacTinhThanh> arrayListPX;
    View view;
    String id_h;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_phuong_xa, container, false);
        AnhXa();

        Bundle bundle = getArguments();
         id_h = bundle.getString("id2");

        String APIQuanHuyen = URL.ApiPhuongXa;

        arrayListPX = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewPX.setLayoutManager(linearLayoutManager);

        addPhuongXaAdapterPX = new AddPhuongXaAdapter(getActivity(), arrayListPX);
        recyclerViewPX.setAdapter(addPhuongXaAdapterPX);

        AddLDM1(APIQuanHuyen);
        return view;
    }
    private void AnhXa(){
        dialog = new ProgressDialog(getActivity());
        recyclerViewPX = (RecyclerView) view.findViewById(R.id.RecyclerviewAddPhuongXa);
    }
    private void AddLDM1(String url){
        dialog.setTitle("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url,
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
                                arrayListPX.add(new CacTinhThanh(
                                        ten, id
                                ));
                            }
                            addPhuongXaAdapterPX.notifyDataSetChanged();
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
                param.put("id", id_h);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
