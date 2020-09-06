package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import hqtrung.hqt.cuoi_ky.Activity.Send_Chat_Activity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.Chat;
import hqtrung.hqt.cuoi_ky.model.ListChat;

public class ListChatAdapter extends RecyclerView.Adapter<ListChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ListChat> arrayList;

    public ListChatAdapter(Context context, ArrayList<ListChat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dong_chat_user, parent, false);
        return new ListChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListChat listChat = arrayList.get(position);
        holder.txtNameUser.setText(listChat.getNameUser().toString());
        holder.txtNameProduct.setText(listChat.getNameSP().toString());
        holder.txtLastMes.setText(listChat.getMessage());
        Picasso.get().load(URL.urlAnh+listChat.getAnhUser().toString()).into(holder.imageViewUser);
        Picasso.get().load(URL.urlAnh+listChat.getAnhSP().toString()).into(holder.imgProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Send_Chat_Activity.class);
                intent.putExtra("idsp", listChat.getIdsp());
                intent.putExtra("idSender", listChat.getIdsender());
                intent.putExtra("nameUser", listChat.getNameUser());
                intent.putExtra("anhUser", listChat.getAnhUser());
                intent.putExtra("sdt", listChat.getPhone());
                context.startActivity(intent);
//                Toast.makeText(context, ""+listChat.getIdsender(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameUser, txtNameProduct, txtLastMes;
        CircleImageView imageViewUser;
        ImageView imgProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameUser = (TextView) itemView.findViewById(R.id.textViewNameChat);
            imageViewUser = (CircleImageView) itemView.findViewById(R.id.circleImageViewUser);
            imgProduct = (ImageView) itemView.findViewById(R.id.imageViewProduct);
            txtNameProduct = (TextView) itemView.findViewById(R.id.textViewChatNameSP);
            txtLastMes = (TextView) itemView.findViewById(R.id.LastMess);
        }
    }
}
