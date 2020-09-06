package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.ActivityHienThiTimKiem;
import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.PhuongXaActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Dia_Chi;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;


public class QuanHuyenActivityAdapter extends RecyclerView.Adapter<QuanHuyenActivityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CacTinhThanh> arrayList;
    public static String AQH = "";

    public QuanHuyenActivityAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public QuanHuyenActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_dong_quan_huyen, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull QuanHuyenActivityAdapter.ViewHolder holder, int position) {
        CacTinhThanh tt = arrayList.get(position);
        holder.txtQH.setText(tt.getName());
        holder.txtQH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityHienThiTimKiem.ok.equals("ok")){
                    AQH = tt.getName();
                    context.startActivity(new Intent(context, ActivityHienThiTimKiem.class));
                }else {
                    AQH = tt.getName();
                    context.startActivity(new Intent(context, Activity_SanPham.class));
                }
            }
        });
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
