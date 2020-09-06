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

import hqtrung.hqt.cuoi_ky.Activity.ActivityHienThiTimKiem;
import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.QuanHuyenActivity;
import hqtrung.hqt.cuoi_ky.Activity.TinhThanhActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Dia_Chi;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

import static android.content.Context.MODE_PRIVATE;

public class TinhThanhActivityAdapter extends RecyclerView.Adapter<TinhThanhActivityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CacTinhThanh> arrayList;
    public static String ATT ="";

    public TinhThanhActivityAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_dong_tinh_thanh, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CacTinhThanh tt = arrayList.get(position);
        holder.txtTT.setText(tt.getName());
        holder.txtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityHienThiTimKiem.ok.equals("ok")){
                    ATT = tt.getName();

                    ActivityHienThiTimKiem.tq = "";

                    String id = String.valueOf(tt.getId());
                    Intent intent = new Intent(context, QuanHuyenActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }else{
                    ATT = tt.getName();

                    Activity_SanPham.editor = Activity_SanPham.preferences.edit();
                    Activity_SanPham.editor.putString("tq", "");
                    Activity_SanPham.editor.apply();


                    String id = String.valueOf(tt.getId());
                    Intent intent = new Intent(context, QuanHuyenActivity.class);
                    intent.putExtra("id", id);
                    context.startActivity(intent);
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTT = (TextView) itemView.findViewById(R.id.textViewDialogTT);
        }
    }
}
