package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.ChiTietSanPhamActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> arrayList;


    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_san_pham, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {

        SanPham sp = arrayList.get(position);
        holder.txtName.setText(sp.getNameSP().toString());
        holder.txtGia.setText(sp.getGiaSP().toString()+ " đ");
        String url = URL.urlAnh+sp.getAnhSP();
        Picasso.get().load(url).into(holder.imgAnh);
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = sp.getId();
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("idsp", id);
                context.startActivity(intent);
            }
        });


//        String json = MainActivity.preferences1.getString("CartList", null);
//        if (json != null) {
//            try {
//                JSONArray jsonArray = new JSONArray(json);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    String id = jsonObject.getString("id");
//                        if (sp.getId().equals(id)){
//                            holder.ibtnTym.setImageResource(R.drawable.ic_favorite_black_24dp);
//                        }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

        holder.ibtnTym.setVisibility(View.GONE);






//        holder.ibtnTym.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Gson gson = new Gson();
//
//                if ( MainActivity.arrayListCart.size() > 0 ){
//
//                    boolean isCheck = false;
//
//                    for ( int i = 0; i < MainActivity.arrayListCart.size(); i++ ){
//                        String id = MainActivity.arrayListCart.get(i).getId();
//                        if ( id.equals(sp.getId())){
//                            MainActivity.arrayListCart.set(i, sp);
//                            isCheck = true;
//                            Toast.makeText(context, "Bạn đã bỏ theo dõi tin", Toast.LENGTH_SHORT).show();
//                            holder.ibtnTym.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                            MainActivity.arrayListCart.remove(i);
//                            MainActivity.preferences1 = context.getSharedPreferences("arrCart", Context.MODE_PRIVATE);
//                            MainActivity.editor1 = MainActivity.preferences1.edit();
//                            String json1 = gson.toJson(MainActivity.arrayListCart);
//                            MainActivity.editor1.putString("CartList", json1);
//                            MainActivity.editor1.apply();
//
//                        }
//
//                    }
//
//                    if ( isCheck == false ){
//                        holder.ibtnTym.setImageResource(R.drawable.ic_favorite_black_24dp);
//                        MainActivity.arrayListCart.add(sp);
//                        Toast.makeText(context, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                    }
//
//                    // lưu vào share
//                    MainActivity.preferences1 = context.getSharedPreferences("arrCart", Context.MODE_PRIVATE);
//                    MainActivity.editor1 = MainActivity.preferences1.edit();
//                    String json = gson.toJson(MainActivity.arrayListCart);
//                    MainActivity.editor1.putString("CartList", json);
//                    MainActivity.editor1.apply();
//
//                }else {
//                    MainActivity.arrayListCart.add(sp);
//                    MainActivity.preferences1 = context.getSharedPreferences("arrCart", Context.MODE_PRIVATE);
//                    MainActivity.editor1 = MainActivity.preferences1.edit();
//                    String json = gson.toJson(MainActivity.arrayListCart);
//                    MainActivity.editor1.putString("CartList", json);
//                    MainActivity.editor1.apply();
//                    holder.ibtnTym.setImageResource(R.drawable.ic_favorite_black_24dp);
//                    Toast.makeText(context, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
//                    notifyDataSetChanged();
//
//                }
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void filterList(ArrayList<SanPham> filterList){
        if (filterList.size() > 0){
            Activity_SanPham.txtTB.setVisibility(View.GONE);
            arrayList = filterList;
            notifyDataSetChanged();
        }else{
            arrayList.clear();
            Activity_SanPham.txtTB.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        }
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
