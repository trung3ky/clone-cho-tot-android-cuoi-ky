package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.ActivityHienThiTimKiem;
import hqtrung.hqt.cuoi_ky.Activity.Activity_Search;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.TimKiem;

public class LichSuTimKiemAdapter extends RecyclerView.Adapter<LichSuTimKiemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TimKiem> arrayList;

    public LichSuTimKiemAdapter(Context context, ArrayList<TimKiem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_lich_su_tim_kiem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimKiem tk = arrayList.get(position);
        holder.txttimkiem.setText(tk.getNoidung());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Search.timkiem = tk.getNoidung();
                Intent intent = new Intent(context, ActivityHienThiTimKiem.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttimkiem;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layoutdongLichsu);
            txttimkiem = (TextView) itemView.findViewById(R.id.TextViewDongLichSu);
        }
    }
}
