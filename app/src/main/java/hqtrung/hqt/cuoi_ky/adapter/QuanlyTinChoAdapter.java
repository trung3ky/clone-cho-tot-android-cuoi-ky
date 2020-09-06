package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
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

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.QuanLyTin;
import hqtrung.hqt.cuoi_ky.model.TinTuChoi;

public class QuanlyTinChoAdapter extends RecyclerView.Adapter<QuanlyTinChoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TinTuChoi> arrayList;

    public QuanlyTinChoAdapter(Context context, ArrayList<TinTuChoi> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_tu_choi, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TinTuChoi sp = arrayList.get(position);
        holder.txtName.setText(sp.getNameSP().toString());
        holder.txtTuChoi.setText(sp.getNoiDung().toString());
        holder.txtgia.setText("Giá:" +sp.getGiaSP() +" đ");
        String url = URL.urlAnh+sp.getAnhSP();
        Picasso.get().load(url).into(holder.imgAnh);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView txtName, txtTuChoi, txtgia;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnh = (ImageView) itemView.findViewById(R.id.imageViewSanPhamTuChoi);
            txtName = (TextView)itemView.findViewById(R.id.textViewNameSPTuChoi);
            txtTuChoi = (TextView) itemView.findViewById(R.id.txttuchoi);
            txtgia = (TextView) itemView.findViewById(R.id.giatuchoi);
        }
    }
}
