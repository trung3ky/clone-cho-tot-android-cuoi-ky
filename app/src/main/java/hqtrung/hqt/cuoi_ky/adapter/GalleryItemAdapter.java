package hqtrung.hqt.cuoi_ky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.model.Picture;

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {

    public interface ItemSelectedChangeListener{
        void onItemSelectedChange(int number);
    }

    private ItemSelectedChangeListener listener;

    private Context context;
    private List<Picture> pictures;
    private List<Picture> picturesSelected;

    int count =0;

    public GalleryItemAdapter(Context context, List<Picture> pictures, ItemSelectedChangeListener listener){
        this.context = context;
        this.pictures = pictures;
        this.listener = listener;
        this.picturesSelected = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_gallery_picture, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(pictures.get(position), position);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPictureItem;
        TextView txtSelectCount;
        ConstraintLayout constraintLayoutItemGallery;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPictureItem = (ImageView) itemView.findViewById(R.id.imageViewPictureItem);
            txtSelectCount = (TextView) itemView.findViewById(R.id.item_gallery_select_count);
            constraintLayoutItemGallery = (ConstraintLayout) itemView.findViewById(R.id.item_gallery);
         }

        public void bind(Picture picture, int position) {
            RequestOptions options = new RequestOptions().skipMemoryCache(true).override(200,200).placeholder(R.drawable.ic_launcher_background);

            Glide.with(context).load(picture.getPath()).apply(options).into(imgPictureItem);

            if (picture.getSelectcount() > 0 ){
                txtSelectCount.setText(picture.getSelectcount()+"");
                txtSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_selected));
            }else{
                txtSelectCount.setText("");
                txtSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_not_selected));
            }

            constraintLayoutItemGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    picture.setPosition(position);
                    if (picture.getSelectcount() > 0 ){
                        count--;
                        txtSelectCount.setText("");
                        txtSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_not_selected));
                        picturesSelected.remove(picture);

                        for (Picture pictureUpdate:pictures){
                            if (pictureUpdate.getSelectcount()>picture.getSelectcount()){
                                pictureUpdate.setSelectcount(pictureUpdate.getSelectcount()-1);
                                notifyItemChanged(pictureUpdate.getPosition());
                            }
                        }
                        picture.setSelectcount(0);
                    }else{
                        if (count >4){
                            Toast.makeText(context, "bạn chỉ được chọn tối đa 5 ảnh", Toast.LENGTH_SHORT).show();
                        }else {
                            count++;
                            picture.setSelectcount(count);
                            txtSelectCount.setText(picture.getSelectcount() + "");
                            txtSelectCount.setBackground(context.getResources().getDrawable(R.drawable.background_count_selected));
                            picturesSelected.add(picture);
                        }
                    }

                    listener.onItemSelectedChange(picturesSelected.size());
                }
            });
        }
    }


    public ArrayList<Picture> getAllPictureSelected(){
        Collections.sort(picturesSelected, new Comparator<Picture>() {
            @Override
            public int compare(Picture o1, Picture o2) {
                return o1.getSelectcount() >=  o2.getSelectcount() ?1:-1;

            }
        });

        return (ArrayList<Picture>) picturesSelected;
    }
}
