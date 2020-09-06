package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Send_Chat_Activity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.Chat;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private ArrayList<Chat> arrayList;

    public MessageAdapter(Context context, ArrayList<Chat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = arrayList.get(position);

        holder.show_message.setText(chat.getMessage());

//        if (imageurl.equals("default")){
//            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
//        }else{
//            Picasso.get().load(imageurl).into(holder.profile_image);
//        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message = (TextView) itemView.findViewById(R.id.show_message);
            profile_image = (ImageView) itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getGender().equals(Send_Chat_Activity.id_nguoi_goi)){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}
