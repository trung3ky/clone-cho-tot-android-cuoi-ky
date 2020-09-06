package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.ChiTietSanPhamActivity;
import hqtrung.hqt.cuoi_ky.Activity.SanPhamTheoLoaiActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class SanPhamTheoLoaiAdapter extends RecyclerView.Adapter<SanPhamTheoLoaiAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> arrayList;

    public SanPhamTheoLoaiAdapter(Context context, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SanPhamTheoLoaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_san_pham, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamTheoLoaiAdapter.ViewHolder holder, int position) {
        SanPham sp = arrayList.get(position);
        holder.txtName.setText(sp.getNameSP().toString());
        holder.txtGia.setText(sp.getGiaSP().toString()+ " đ");
        String url = URL.urlAnh+sp.getAnhSP();
        Picasso.get().load(url).into(holder.imgAnh);
        holder.ibtnTym.setVisibility(View.GONE);
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = sp.getId();
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("idsp", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<SanPham> filterList){
        if (filterList.size() > 0){
            SanPhamTheoLoaiActivity.txtTB.setVisibility(View.GONE);
            arrayList = filterList;
            notifyDataSetChanged();
        }else{
            arrayList.clear();
            SanPhamTheoLoaiActivity.txtTB.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView txtName, txtGia;
        ImageButton ibtnTym;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = (ImageView) itemView.findViewById(R.id.imageViewSanPham);
            txtName = (TextView) itemView.findViewById(R.id.textViewNameSP);
            txtGia = (TextView) itemView.findViewById(R.id.textViewGiaSP);
            ibtnTym = (ImageButton) itemView.findViewById(R.id.imageButtonTym);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layoutsSanPham);

        }
    }
}
