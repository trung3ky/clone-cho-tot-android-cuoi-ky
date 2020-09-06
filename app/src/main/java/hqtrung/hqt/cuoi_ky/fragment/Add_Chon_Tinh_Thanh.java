package hqtrung.hqt.cuoi_ky.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import hqtrung.hqt.cuoi_ky.adapter.AddLoaiDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddTinhThanhAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class Add_Chon_Tinh_Thanh extends Fragment {
    ProgressDialog dialog;

    String APITinhThanh = URL.ApiTinhThanh;
    RecyclerView recyclerViewTT;
    AddTinhThanhAdapter addTinhThanhAdapterTT;
    ArrayList<CacTinhThanh> arrayListTT;


    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_tinh_thanh, container, false);
        AnhXa();

        arrayListTT = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewTT.setLayoutManager(linearLayoutManager);

        addTinhThanhAdapterTT = new AddTinhThanhAdapter(getActivity(), arrayListTT);
        recyclerViewTT.setAdapter(addTinhThanhAdapterTT);

        AddLDM(APITinhThanh);
        return view;
    }
    private void AnhXa() {
        dialog = new ProgressDialog(getActivity());
        recyclerViewTT = (RecyclerView) view.findViewById(R.id.RecyclerviewAddTinhThanh);

    }
    private void AddLDM(String url){
        dialog.setTitle("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
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
                                arrayListTT.add(new CacTinhThanh(
                                        ten, id
                                ));
                            }
                            addTinhThanhAdapterTT.notifyDataSetChanged();
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
                });
        requestQueue.add(stringRequest);
    }
}
