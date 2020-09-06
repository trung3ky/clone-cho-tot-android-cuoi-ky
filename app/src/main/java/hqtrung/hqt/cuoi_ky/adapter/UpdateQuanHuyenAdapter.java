package hqtrung.hqt.cuoi_ky.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import hqtrung.hqt.cuoi_ky.Activity.ActivitySuaSanPham;
import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class UpdateQuanHuyenAdapter extends RecyclerView.Adapter<UpdateQuanHuyenAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CacTinhThanh> arrayList;

    ArrayList<CacTinhThanh> arrayListPX;
    RecyclerView recyclerViewPX;
    UpdatePhuongXaAdapter adapterPX;

    String id;
    String APIQuanHuyen = URL.ApiPhuongXa;
    public static Dialog dialog;

    public UpdateQuanHuyenAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_dong_quan_huyen, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CacTinhThanh tt = arrayList.get(position);
        holder.txtQH.setText(tt.getName());
        holder.txtQH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySuaSanPham.edtQH.setText(tt.getName());
                UpdateTinhThanhAdapter.dialog.dismiss();
                id = tt.getId()+"";
                DialogPhuongXa();
            }
        });
    }

    private  void DialogPhuongXa(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_phuong_xa);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        recyclerViewPX = (RecyclerView) dialog.findViewById(R.id.RecyclerviewAddPhuongXa);
        arrayListPX =new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewPX.setLayoutManager(linearLayoutManager);

        adapterPX = new UpdatePhuongXaAdapter(context, arrayListPX);
        recyclerViewPX.setAdapter(adapterPX);

        PhuongXa(APIQuanHuyen);

        dialog.show();
    }

    private void PhuongXa(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest  = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                            adapterPX.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lõi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "lỗi" + error);
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtQH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtQH = (TextView) itemView.findViewById(R.id.textViewDialogQH);
        }
    }
}
