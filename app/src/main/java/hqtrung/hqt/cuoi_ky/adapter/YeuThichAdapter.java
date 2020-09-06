package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.ChiTietSanPhamActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Notification;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> arrayList;


    public YeuThichAdapter(Context context, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public YeuThichAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context  = parent.getContext();
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_san_pham, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(@NonNull YeuThichAdapter.ViewHolder holder, int position) {
        SanPham sp = arrayList.get(position);
        holder.txtName.setText(sp.getNameSP().toString());
        holder.txtGia.setText(sp.getGiaSP().toString()+ " đ");
        String url = URL.urlAnh+sp.getAnhSP();
        Picasso.get().load(url).into(holder.imgAnh);
        if (sp.isTym() == true){
            holder.ibtnTym.setImageResource(R.drawable.ic_favorite_black_24dp);
        }

        holder.ibtnTym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = MainActivity.preferences1.getString("CartList", null);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for(int i =0 ; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (jsonObject.getString("id".toString()).equals(sp.getId())){
                            MainActivity.arrayListCart.remove(i);
                            MainActivity.preferences1 = context.getSharedPreferences("arrCart", Context.MODE_PRIVATE);
                            MainActivity.editor1 = MainActivity.preferences1.edit();
                            String json1 = gson.toJson(MainActivity.arrayListCart);
                            MainActivity.editor1.putString("CartList", json1);
                            MainActivity.editor1.apply();
                            if (MainActivity.arrayListCart.size() < 1){
                                Activity_Notification.txtDSYT.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("idsp", sp.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnh;
        TextView txtName, txtGia;
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
