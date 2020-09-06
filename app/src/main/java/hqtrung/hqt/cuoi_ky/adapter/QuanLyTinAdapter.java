package hqtrung.hqt.cuoi_ky.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.ActivitySuaSanPham;
import hqtrung.hqt.cuoi_ky.Activity.AddActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Home;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Sale;
import hqtrung.hqt.cuoi_ky.model.QuanLyTin;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class QuanLyTinAdapter extends RecyclerView.Adapter<QuanLyTinAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QuanLyTin> arrayList;
    Dialog dialog;
    String id;
    String urlXoa = URL.urlXoaSP;
    public static String idSanPham;


    public QuanLyTinAdapter(Context context, ArrayList<QuanLyTin> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_san_pham, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuanLyTin sp = arrayList.get(position);
        holder.txtName.setText(sp.getNameSP().toString());
        holder.txtGia.setText(sp.getGiaSP().toString()+ " đ");
        String url = URL.urlAnh+sp.getAnhSP();
        Picasso.get().load(url).into(holder.imgAnh);
        holder.txtName.setLines(1);
        holder.ibtnTym.setVisibility(View.GONE);
        holder.imgMore.setVisibility(View.VISIBLE);
        holder.date.setVisibility(View.VISIBLE);
        holder.date.setText(sp.getDate());


        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = sp.getId();
                Dailog();
            }
        });
    }

    private void Dailog(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_sua_tin);

        TextView txtsuatin = (TextView) dialog.findViewById(R.id.sua_tin);
        TextView txtantin = (TextView) dialog.findViewById(R.id.an_tin);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        window.setAttributes(wlp);

        txtsuatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txtantin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogtb = new AlertDialog.Builder(context);
                dialogtb.setTitle("Thông báo");
                dialogtb.setMessage("Bạn có chắc muốn xóa tin này không?");
                dialogtb.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Delete(urlXoa);
                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Activity_Sale()).commit();
                    }
                });
                dialogtb.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.dismiss();

                dialogtb.show();
            }
        });

        txtsuatin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivitySuaSanPham.class);
                idSanPham = id;
                context.startActivity(intent);
            }
        });
        dialog.show();
    }

    private void Delete(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Bạn đã hủy tin thành công", Toast.LENGTH_SHORT).show();
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
                param.put("idsp", id);
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
        ImageView imgAnh, imgMore;
        TextView txtName, txtGia, date;
        ImageButton ibtnTym;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = (ImageView) itemView.findViewById(R.id.imageViewSanPham);
            txtName = (TextView) itemView.findViewById(R.id.textViewNameSP);
            txtGia = (TextView) itemView.findViewById(R.id.textViewGiaSP);
            ibtnTym = (ImageButton) itemView.findViewById(R.id.imageButtonTym);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layoutsSanPham);
            imgMore = (ImageView) itemView.findViewById(R.id.ImageViewMore);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }
}
