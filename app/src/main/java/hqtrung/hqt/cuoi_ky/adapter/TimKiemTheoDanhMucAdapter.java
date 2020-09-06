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
import hqtrung.hqt.cuoi_ky.Activity.TimkiemTheoLoaiDanhMucActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class TimKiemTheoDanhMucAdapter extends RecyclerView.Adapter<TimKiemTheoDanhMucAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DanhMuc> arrayList;

    public TimKiemTheoDanhMucAdapter(Context context, ArrayList<DanhMuc> arrayList) {
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
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHienThiTimKiem.danhmuc = danhMuc.getTenDanhMuc();
                String id = danhMuc.getId()+"";
                String ten = danhMuc.getTenDanhMuc();
                Intent intent = new Intent(context, TimkiemTheoLoaiDanhMucActivity.class);
                intent.putExtra("danhmuc", id);
                intent.putExtra("tendanhmuc", ten);
                context.startActivity(intent);
            }
        });
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
