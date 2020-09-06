package hqtrung.hqt.cuoi_ky.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.until.ConstantDataManager;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.adapter.GalleryItemAdapter;
import hqtrung.hqt.cuoi_ky.model.Picture;
import hqtrung.hqt.cuoi_ky.until.Libaries;

public class Add_select_image extends Fragment {
    private RecyclerView recyclerViewGallery;
    private ArrayList<Picture> pictures;
    GalleryItemAdapter adapter;
    Handler handler;

    private ImageView imgButtonSend;
    private TextView txtSelectedCount;
    private ConstraintLayout constraintLayoutSend;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.multiple_image,container, false);

        imgButtonSend = (ImageView) view.findViewById(R.id.button_send);
        txtSelectedCount = (TextView) view.findViewById(R.id.textviewSelectCount);
        constraintLayoutSend = (ConstraintLayout) view.findViewById(R.id.layoutSend);

        imgButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_Chon_Anh();
                ArrayList<Picture> pictureSelect = adapter.getAllPictureSelected();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("selected", (ArrayList<? extends Parcelable>) pictureSelect);
                fragment.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        pictures = new ArrayList<>();
        recyclerViewGallery = (RecyclerView) view.findViewById(R.id.RecyclerviewGallery);
        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new GalleryItemAdapter(getActivity(), pictures, new GalleryItemAdapter.ItemSelectedChangeListener() {
            @Override
            public void onItemSelectedChange(int number) {
                if (number>0){
                    constraintLayoutSend.setVisibility(View.VISIBLE);
                    txtSelectedCount.setText(number+"");
                }else{
                    constraintLayoutSend.setVisibility(View.GONE);
                }
            }
        });
        recyclerViewGallery.setAdapter(adapter);

        handler = new Handler();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Libaries.requestPermissionStorage(getActivity());
        }else {
           new Thread(){
               @Override
               public void run() {
                   Looper.prepare();
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           pictures.clear();
                           pictures.addAll(Picture.getGalleryPhotos(getActivity()));
                           adapter.notifyDataSetChanged();
                       }
                   });
                   Looper.loop();
               }
           }.start();
        }


        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantDataManager.PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE){
            if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                new Thread(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pictures.clear();
                                pictures.addAll(Picture.getGalleryPhotos(getActivity()));
                                adapter.notifyDataSetChanged();
                            }
                        });
                        Looper.loop();
                    }
                }.start();
            }else{
                Libaries.requestPermissionStorageDeny(getActivity());
            }
        }
    }
}
