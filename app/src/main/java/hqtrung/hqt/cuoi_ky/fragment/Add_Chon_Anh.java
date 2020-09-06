package hqtrung.hqt.cuoi_ky.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import hqtrung.hqt.cuoi_ky.Activity.AddActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.adapter.ImageSelectedAdapter;

import static android.app.Activity.RESULT_OK;

public class Add_Chon_Anh extends Fragment {
    View view;
    private ImageView imgChonAnh, imageChuaAnh, imgBack;
    private Button btnXN;
    public int Request_Code_Image = 123;
    public  static  Bitmap photo;
    public static  String image;
    public static  String Nameimage;

    RecyclerView recyclerView;
    ImageSelectedAdapter adapter;

    public int PICK_IMAGES = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.add_chon_anh, container, false);
        AnhXa();
        return  view;
    }

    private void AnhXa() {
        imgChonAnh = (ImageView) view.findViewById(R.id.imageAddChonHinhAnh);
        imageChuaAnh = (ImageView) view.findViewById(R.id.ImageAddHinhAnh);
        btnXN = (Button) view.findViewById(R.id.btnXacNhanHinhAnh);
        recyclerView = (RecyclerView) view.findViewById(R.id.RecyclerviewSelected);
        imgBack = (ImageView) view.findViewById(R.id.ImageViewAddCAnh);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_Dia_Chi();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList picturesSelected = bundle.getParcelableArrayList("selected");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter = new ImageSelectedAdapter(getActivity(), picturesSelected);
            recyclerView.setAdapter(adapter);
            btnXN.setVisibility(View.VISIBLE);
        }
        imgChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Intent  intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);

//                Fragment fragment = new Add_select_image();
//
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                activity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
//                        .commit();
            }
        });

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                        new Add_Info_SanPham()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null){
            recyclerView = null;
            adapter = null;
            Runtime.getRuntime().gc();
        }
    }

    private void requestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGES);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(getContext())
        .setPermissionListener(permissionlistener)
        .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
        .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
        .check();
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == PICK_IMAGES) {
//                if (data.getClipData() != null) {
//                    ClipData mClipData = data.getClipData();
//                    for (int i = 0; i < mClipData.getItemCount(); i++) {
//                        ClipData.Item item = mClipData.getItemAt(i);
//                        Uri uri = item.getUri();
//                        // display your images
//                        imageChuaAnh.setImageURI(uri);
//                    }
//                } else if (data.getData() != null) {
//                    Uri uri = data.getData();
//                    // display your image
//                    imageChuaAnh.setImageURI(uri);
//                }
//            }
//        }
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_Code_Image && resultCode ==  RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                photo = BitmapFactory.decodeStream(inputStream);
                imageChuaAnh.setImageBitmap(photo);
                image = getStringImage(photo);
                Nameimage = System.currentTimeMillis()+"";
                btnXN.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            photo = (Bitmap) data.getExtras().get("data");
//            imageChuaAnh.setImageBitmap(photo);
//            image = getStringImage(photo);
//            Nameimage = System.currentTimeMillis()+"";
//            btnXN.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getStringImage(Bitmap bm){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }
}
