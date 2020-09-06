package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import hqtrung.hqt.cuoi_ky.Activity.TinhThanhActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_Dia_Chi;
import hqtrung.hqt.cuoi_ky.fragment.Add_chon_Loai_Dang_Tin;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;

public class AddTinhThanhAdapter extends RecyclerView.Adapter<AddTinhThanhAdapter.ViewHolder> {
    public static String ten;
    public static String id;
    private Context context;
    private ArrayList<CacTinhThanh> arrayList;
    public  static Bundle bundle;

    String tenDM = AddDanhMucAdapter.tenDM.toString();



    public AddTinhThanhAdapter(Context context, ArrayList<CacTinhThanh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AddTinhThanhAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_dong_tinh_thanh, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddTinhThanhAdapter.ViewHolder holder, int position) {
        CacTinhThanh tt = arrayList.get(position);
        holder.txtTT.setText(tt.getName());
        holder.txtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        ten = tt.getName();
                        id = tt.getId()+"";
                        Fragment fragment = new Add_Dia_Chi();


                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                                .commit();

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
