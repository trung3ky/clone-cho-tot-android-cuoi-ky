package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.ActivitySuaSanPham;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Dia_Chi;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class UpdatePhuongXaAdapter extends RecyclerView.Adapter<UpdatePhuongXaAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CacTinhThanh> arrayList;

    public UpdatePhuongXaAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_dong_phuong_xa, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CacTinhThanh PX = arrayList.get(position);
        holder.txtPX.setText(PX.getName());
        holder.txtPX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySuaSanPham.edtPX.setText(PX.getName());
                UpdateQuanHuyenAdapter.dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPX;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPX = (TextView) itemView.findViewById(R.id.textViewDialogPX);
        }
    }
}
