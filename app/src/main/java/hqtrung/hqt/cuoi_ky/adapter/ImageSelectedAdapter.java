package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.Picture;

public class ImageSelectedAdapter extends RecyclerView.Adapter<ImageSelectedAdapter.ViewHolder> {
    private Context context;
    private List<Picture> pictures;
    public static String image;
    public static String Nameimage;

    public ImageSelectedAdapter(Context context,List<Picture> pictures){
        this.context=context;
        this.pictures=pictures;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_image_selected, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(pictures.get(position));
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtht;
        ImageView imageViewSeleted;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSeleted=itemView.findViewById(R.id.imageViewPictureSelectedItem);
            txtht = (TextView) itemView.findViewById(R.id.ht);
        }

        public void bind(Picture picture) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200).placeholder(R.drawable.ic_launcher_background);
            Glide.with(context)
                    .load(picture.getPath())
                    .apply(options)
                    .into(imageViewSeleted);
            Uri uri = Uri.parse(picture.getPath());
            InputStream inputStream = null;
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                image = getStringImage(photo);
                Nameimage = System.currentTimeMillis()+"";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImage(Bitmap bm){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }
}
