package hqtrung.hqt.cuoi_ky.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.ActivitySuaSanPham;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.fragment.Add_loai_danh_muc;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class UpdateDanhMucAdapter extends RecyclerView.Adapter<UpdateDanhMucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhMuc> arrayList;

    public static Dialog dialog;
    RecyclerView recyclerViewLDM;
    ArrayList<LoaiDanhMuc> arrayListLDM;
    UpdateLoaiDanhMucAdapter adapterLDM;

    String UrlLoaiDM = URL.urlSanPham;
    String id;

    public UpdateDanhMucAdapter(Context context, ArrayList<DanhMuc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_dong_danh_muc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc danhMuc = arrayList.get(position);
        holder.txtAddDM.setText(danhMuc.getTenDanhMuc());

        holder.txtAddDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = danhMuc.getId()+"";
                ActivitySuaSanPham.edtDM.setText(danhMuc.getTenDanhMuc());
                ActivitySuaSanPham.dialog.dismiss();

                Showdialog();
            }
        });
    }

    private void Showdialog(){
        ImageView imageView;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_loai_danh_muc);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        imageView = (ImageView) dialog.findViewById(R.id.ImageViewAddLDM);
        imageView.setVisibility(View.GONE);

        recyclerViewLDM = (RecyclerView) dialog.findViewById(R.id.RecyclerviewAddLoaiDanhMuc);
        arrayListLDM =new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewLDM.setLayoutManager(linearLayoutManager);

        adapterLDM = new UpdateLoaiDanhMucAdapter(context, arrayListLDM);
        recyclerViewLDM.setAdapter(adapterLDM);
        addLDM(UrlLoaiDM);
        dialog.show();
    }

    private void addLDM(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
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
                                arrayListLDM.add(new LoaiDanhMuc(
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
                        Toast.makeText(context, "Lõi hệ thống", Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtAddDM;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddDM = (TextView) itemView.findViewById(R.id.textViewAddDanhMuc);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }
}
