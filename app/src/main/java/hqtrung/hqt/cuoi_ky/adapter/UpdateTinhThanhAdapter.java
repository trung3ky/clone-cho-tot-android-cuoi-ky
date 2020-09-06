package hqtrung.hqt.cuoi_ky.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import hqtrung.hqt.cuoi_ky.Activity.QuanHuyenActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class UpdateTinhThanhAdapter extends RecyclerView.Adapter<UpdateTinhThanhAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CacTinhThanh> arrayList;

    ArrayList<CacTinhThanh> arrayListQH;
    RecyclerView recyclerViewQH;
    UpdateQuanHuyenAdapter adapterQH;

    String APIQuanHuyen = URL.ApiQuanHuyen;
    public static  Dialog dialog;

    String id;

    public UpdateTinhThanhAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_dong_tinh_thanh, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CacTinhThanh tt = arrayList.get(position);
        holder.txtTT.setText(tt.getName());
        holder.txtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySuaSanPham.edtTT.setText(tt.getName());
                ActivitySuaSanPham.dialog.dismiss();
                id = tt.getId()+"";
                DialogQuanHuyen();
            }
        });
    }

    private void DialogQuanHuyen(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_quan_huyen);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        recyclerViewQH = (RecyclerView) dialog.findViewById(R.id.RecyclerviewAddQuanHuyen);
        arrayListQH =new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewQH.setLayoutManager(linearLayoutManager);

        adapterQH = new UpdateQuanHuyenAdapter(context, arrayListQH);
        recyclerViewQH.setAdapter(adapterQH);

        QuanHUyen(APIQuanHuyen);

        dialog.show();
    }

    private void QuanHUyen(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int i = 0; i< array.length(); i++){
                                JSONObject object1 = array.getJSONObject(i);
                                int id = object1.getInt("id");
                                String ten = object1.getString("name");
                                arrayListQH.add(new CacTinhThanh(
                                        ten, id
                                ));
                            }
                            adapterQH.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
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
        TextView txtTT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTT = (TextView) itemView.findViewById(R.id.textViewDialogTT);
        }
    }
}
