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

public class QuanlyTinDuyetAdapter extends RecyclerView.Adapter<QuanlyTinDuyetAdapter.ViewHolder> {
    private Context context;
    private ArrayList<QuanLyTin> arrayList;

    public QuanlyTinDuyetAdapter(Context context, ArrayList<QuanLyTin> arrayList) {
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
        holder.ibtnTym.setVisibility(View.GONE);
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
        }
    }
}
