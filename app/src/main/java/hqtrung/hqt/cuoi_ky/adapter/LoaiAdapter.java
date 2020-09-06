package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.SanPhamTheoLoaiActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiDanhMuc> arrayList;

    public LoaiAdapter(Context context, ArrayList<LoaiDanhMuc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.dong_thanh_phan_danh_muc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiDanhMuc loaiDanhMuc = arrayList.get(position);
        holder.txtLoai.setText(loaiDanhMuc.getName());
        Picasso.get().load(loaiDanhMuc.getAnh()).into(holder.imgLoai);
        holder.imgLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idLoai = String.valueOf(loaiDanhMuc.getId());
                Intent intent = new Intent(context, SanPhamTheoLoaiActivity.class);
                intent.putExtra("idLoai", idLoai);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLoai;
        TextView txtLoai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLoai = (ImageView) itemView.findViewById(R.id.imageViewLoai);
            txtLoai = (TextView) itemView.findViewById(R.id.textViewLoai);
        }

    }
}
