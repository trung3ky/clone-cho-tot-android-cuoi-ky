package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Dia_Chi;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class PhuongXaActivityAdapter extends RecyclerView.Adapter<PhuongXaActivityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CacTinhThanh> arrayList;
    public static String APX;

    public PhuongXaActivityAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public PhuongXaActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_add_dong_phuong_xa, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhuongXaActivityAdapter.ViewHolder holder, int position) {
        CacTinhThanh PX = arrayList.get(position);
        holder.txtPX.setText(PX.getName());
        holder.txtPX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                APX = PX.getName();
               context.startActivity(new Intent(context, Activity_SanPham.class));
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
