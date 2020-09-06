package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_loai_danh_muc;
import hqtrung.hqt.cuoi_ky.fragment.SignUpFragment;
import hqtrung.hqt.cuoi_ky.fragment.Upload_SanPham;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class AddDanhMucAdapter extends RecyclerView.Adapter<AddDanhMucAdapter.ViewHolder> {
    public static String tenDM = "";
    public static String id= "";
    private Context context;
    private ArrayList<DanhMuc> arrayList;

    public AddDanhMucAdapter(Context context, ArrayList<DanhMuc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public AddDanhMucAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_dong_danh_muc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddDanhMucAdapter.ViewHolder holder, int position) {
        DanhMuc danhMuc = arrayList.get(position);
        holder.txtAddDM.setText(danhMuc.getTenDanhMuc());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenDM = danhMuc.getTenDanhMuc().toString();

                // chuyển dữ liệu đến loại danh mục
                Fragment fragment = new Add_loai_danh_muc();
                id = danhMuc.getId()+"";
//                Bundle bundle = new Bundle();
//                bundle.putInt("id", id);
//                fragment.setArguments(bundle);

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
        TextView txtAddDM;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAddDM = (TextView) itemView.findViewById(R.id.textViewAddDanhMuc);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout);
        }
    }
}
