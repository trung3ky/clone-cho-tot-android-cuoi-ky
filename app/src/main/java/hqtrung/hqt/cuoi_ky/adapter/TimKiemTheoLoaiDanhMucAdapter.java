package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.ActivityHienThiTimKiem;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class TimKiemTheoLoaiDanhMucAdapter extends RecyclerView.Adapter<TimKiemTheoLoaiDanhMucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiDanhMuc> arrayList;

    public TimKiemTheoLoaiDanhMucAdapter(Context context, ArrayList<LoaiDanhMuc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_dong_loai_danh_muc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiDanhMuc LDM = arrayList.get(position);
        holder.txtLoaiDM.setText(LDM.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.loaidanhmuc = LDM.getName();
                ActivityHienThiTimKiem.AllDanhMuc = "";
                ActivityHienThiTimKiem.AllLoaiDanhMuc = "";
                context.startActivity(new Intent(context, ActivityHienThiTimKiem.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLoaiDM;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoaiDM = (TextView) itemView.findViewById(R.id.textViewAddLoaiDM);
            layout = (RelativeLayout) itemView.findViewById(R.id.layoutLoaiDM);
        }
    }
}
