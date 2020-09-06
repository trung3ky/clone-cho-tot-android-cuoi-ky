package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Add_chon_Loai_Dang_Tin;
import hqtrung.hqt.cuoi_ky.fragment.Add_loai_danh_muc;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class AddLoaiDanhMucAdapter extends RecyclerView.Adapter<AddLoaiDanhMucAdapter.ViewHolder> {
    public static String tenLDM = "";
    public static int id;
    private Context context;
    private ArrayList<LoaiDanhMuc> arrayList;

    public AddLoaiDanhMucAdapter(Context context, ArrayList<LoaiDanhMuc> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AddLoaiDanhMucAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.add_dong_loai_danh_muc, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddLoaiDanhMucAdapter.ViewHolder holder, int position) {
        LoaiDanhMuc LDM = arrayList.get(position);
        holder.txtLoaiDM.setText(LDM.getName());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenLDM = LDM.getName().toString();


                Fragment fragment = new Add_chon_Loai_Dang_Tin();

                id = LDM.getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                fragment.setArguments(bundle);

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
        TextView txtLoaiDM;
        RelativeLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLoaiDM = (TextView) itemView.findViewById(R.id.textViewAddLoaiDM);
            layout = (RelativeLayout) itemView.findViewById(R.id.layoutLoaiDM);
        }
    }
}
