package hqtrung.hqt.cuoi_ky.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import hqtrung.hqt.cuoi_ky.Activity.AboutUs_Activity;
import hqtrung.hqt.cuoi_ky.Activity.AddActivity;
import hqtrung.hqt.cuoi_ky.Activity.AuthActivity;
import hqtrung.hqt.cuoi_ky.Activity.CaiDatTaiKhoanActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.Activity.ThongTinKhachHangActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;

public class Activity_More extends Fragment {
    ProgressDialog dialog1;
    View view;
    TextView txtUserName, txtDnDk, txtTDL,txtDangBan, txtcaidat,txtAboutUs;
    ImageView ThayDoiHA;
    TextView btnDX;
    CircleImageView img;
    Dialog dialog;
    String urlanh;
    String nameanh;
    String upload = URL.upload;
    public static String iduser;

    final int RESQUEST_CODE_CAMERA = 111;
    final int REQUEST_CODE_FOLDER = 222;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_more, container, false);
        AnhXa();
        String user = MainActivity.preferences.getString("user_name", "");
        String hinhanh = MainActivity.preferences.getString("hinhanh", "");
        iduser = MainActivity.preferences.getString("id", "");
        if (user.isEmpty()){
            txtUserName.setText("Wellcome to Cho Viet");
            btnDX.setVisibility(View.GONE);
            ThayDoiHA.setVisibility(View.GONE);
            img.setPadding(0,0,40,0 );
            txtDnDk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), AuthActivity.class));
                }
            });
        }else {
            txtUserName.setText(user);
            btnDX.setVisibility(View.VISIBLE);
            txtDnDk.setVisibility(View.GONE);
            btnDX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.editor = MainActivity.preferences.edit();
                    MainActivity.editor.clear();
                    MainActivity.editor.apply();
//                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Activity_More()).commit();
                    MainActivity.bottomNavigationView.setSelectedItemId(R.id.page_4);
                }
            });
            txtUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ThongTinKhachHangActivity.class);
                    intent.putExtra("id", iduser);
                    startActivity(intent);
                }
            });
            txtcaidat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CaiDatTaiKhoanActivity.class);
                    intent.putExtra("id", iduser);
                    startActivity(intent);
                }
            });
        }
        if (!hinhanh.isEmpty()){
            Picasso.get().load(URL.urlAnh+hinhanh).into(img);
        }
        return view;
    }

    private void AnhXa() {
        txtUserName = (TextView) view.findViewById(R.id.user_name);
        txtDnDk = (TextView) view.findViewById(R.id.dangnhap_id);
        btnDX = (TextView) view.findViewById(R.id.btnLogout);
        ThayDoiHA = (ImageView) view.findViewById(R.id.ThayDoiHinhAnh);
        img = (CircleImageView) view.findViewById(R.id.HinhAnhUser);
        txtTDL = (TextView) view.findViewById(R.id.tindangluu);
        txtDangBan = (TextView) view.findViewById(R.id.DangBanuser);
        txtcaidat = (TextView) view.findViewById(R.id.caidat);
        txtAboutUs = (TextView) view.findViewById(R.id.vechungtoi);
        dialog1 = new ProgressDialog(getActivity());


        dialog1.setMessage("Đang upload sản phẩm, làm ơn chờ");
        dialog1.setCancelable(false);
        dialog1.setMax(100);
        dialog1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        ThayDoiHA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dailog();
            }
        });
        txtTDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.page_3);
                Fragment fragment = new Activity_Notification();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout, fragment)
                        .commit();
            }
        });
        txtDangBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.page_2);
                Fragment fragment = new Activity_Sale();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayout, fragment)
                        .commit();
            }
        });

        txtAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUs_Activity.class));
            }
        });

    }

    private void Dailog(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.chon_anh);
        dialog.setCanceledOnTouchOutside(false);

        TextView txtChonAnh = (TextView) dialog.findViewById(R.id.textViewChonAnh);
        TextView txtChupAnh = (TextView) dialog.findViewById(R.id.textViewChupAnh);
        TextView txtHuy = (TextView) dialog.findViewById(R.id.textViewHUY);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        window.setAttributes(wlp);

        txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESQUEST_CODE_CAMERA);
            }
        });

        txtChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESQUEST_CODE_CAMERA && resultCode == getActivity().RESULT_OK && data != null ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            urlanh = getStringImage(bitmap);
            nameanh = "user"+System.currentTimeMillis()+"";
            dialog1.show();
            uploadAnh(upload);
            dialog.dismiss();
            img.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == getActivity().RESULT_OK && data != null){
            Uri uri =data.getData();
            try {
                InputStream inputStream =getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                urlanh = getStringImage(bitmap);
                nameanh = "user"+System.currentTimeMillis()+"";
                dialog1.show();
                uploadAnh(upload);
                dialog.dismiss();
                img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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

    private void uploadAnh(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong")==true){
                                MainActivity.preferences = getActivity().getSharedPreferences("user", getActivity().MODE_PRIVATE);
                                MainActivity.editor = MainActivity.preferences.edit();
                                MainActivity.editor.putString("hinhanh", nameanh+".jpg");
                                MainActivity.editor.apply();
                                dialog1.dismiss();
                                Toast.makeText(getActivity(), "Thay đổi ảnh thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog1.dismiss();
                                Toast.makeText(getActivity(), "Thay đổi ảnh không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog1.dismiss();
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("nameanh", nameanh);
                param.put("urlanh", urlanh);
                param.put("iduser", iduser);
                return param;
            }
        };

        requestQueue.add(stringRequest);

    }

}
