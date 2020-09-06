package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Notification;
import hqtrung.hqt.cuoi_ky.model.ChucNang;

public class ChucNangAdapter extends RecyclerView.Adapter<ChucNangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ChucNang> arrayList;

    public ChucNangAdapter(Context context, ArrayList<ChucNang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_chuc_nang, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChucNang chucNang = arrayList.get(position);
        holder.imgIconCN.setImageResource(chucNang.getIcon());
        holder.txtTenCN.setText(chucNang.getTenChucNang());
        int id = chucNang.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == 2){
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.page_3);
                    Fragment fragment = new Activity_Notification();
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.FrameLayout, fragment)
                            .commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenCN;
        ImageView imgIconCN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenCN = (TextView) itemView.findViewById(R.id.textViewTenChucNang);
            imgIconCN = (ImageView) itemView.findViewById(R.id.imageViewIconChucNang);
        }
    }
}
