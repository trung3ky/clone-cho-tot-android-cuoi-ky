package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.model.SanPham;

import static android.Manifest.permission.CALL_PHONE;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    String idsp;
    ImageView imgSP, imgBack, imgtym;
    CircleImageView ANguoiDang;
    TextView txtName, txtGia, txtUser, txtMota;
    Button btnGoi, btnSMS, btnChat;
    ImageView imgTin;
    LinearLayout layoutTin;

    String urlSP = URL.urlChiTietSanPham;
    String sdt;
    SanPham sp;
    String idUser;

    DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Intent intent = getIntent();
        idsp = intent.getStringExtra("idsp");

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");

        AnhXa();
//        kt();
        String json2 = MainActivity.preferences1.getString("CartList", null);
        if (json2 !=null) {
            try {
                JSONArray jsonArray = new JSONArray(json2);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("id").equals(idsp)) {
                        imgTin.setImageResource(R.drawable.ic_favorite_black_24dp);
                        imgtym.setImageResource(R.drawable.ic_favorite_black_24dp);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        htSanPham(urlSP);
    }

    private void AnhXa() {
        imgBack = (ImageView) findViewById(R.id.ImageViewChiTietSP);
        imgSP = (ImageView) findViewById(R.id.ImageViewChiTietSanPham);
        txtName = (TextView) findViewById(R.id.TextViewChiTietSanPhamName);
        txtGia = (TextView) findViewById(R.id.TextViewChiTietSanPhamGia);
        txtUser = (TextView) findViewById(R.id.TextViewChiTietSanPhamUser);
        txtMota = (TextView) findViewById(R.id.TextViewChiTietSanPhamMoTa);
        btnChat = (Button) findViewById(R.id.ButtonChiTietChat);
        btnGoi = (Button) findViewById(R.id.ButtonChiTietGoi);
        btnSMS = (Button) findViewById(R.id.ButtonChiTietSMS);
        layoutTin = (LinearLayout) findViewById(R.id.LayoutTin);
        imgTin = (ImageView) findViewById(R.id.ImageViewTin);
        ANguoiDang = (CircleImageView) findViewById(R.id.AnhnguoiDang);
        imgtym = (ImageView) findViewById(R.id.tymtoolbar);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, ThongTinKhachHangActivity.class);
                intent.putExtra("id", idUser);
                startActivity(intent);
            }
        });

        btnGoi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+sdt));
                startActivity(callIntent);
            }
        });

        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("sms:"+sdt));
                startActivity(smsIntent);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSanPhamActivity.this, Send_Chat_Activity.class);
                intent.putExtra("idsp", idsp);
                intent.putExtra("idSender", "");
                intent.putExtra("nameUser", "");
                intent.putExtra("anhUser", "");
                intent.putExtra("sdt", "");
                startActivity(intent);
            }
        });

        layoutTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tym();
            }
        });


    }

    private void Tym(){
        Gson gson = new Gson();

        if ( MainActivity.arrayListCart.size() > 0 ){

            boolean isCheck = false;

            for ( int i = 0; i < MainActivity.arrayListCart.size(); i++ ){
                String id = MainActivity.arrayListCart.get(i).getId();
                if ( id.equals(sp.getId())){
                    MainActivity.arrayListCart.set(i, sp);
                    isCheck = true;
                    Toast.makeText(this, "Bạn đã bỏ theo dõi tin", Toast.LENGTH_SHORT).show();
                    imgTin.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    imgtym.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    MainActivity.arrayListCart.remove(i);
                    MainActivity.preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
                    MainActivity.editor1 = MainActivity.preferences1.edit();
                    String json1 = gson.toJson(MainActivity.arrayListCart);
                    MainActivity.editor1.putString("CartList", json1);
                    MainActivity.editor1.apply();

                }

            }

            if ( isCheck == false ){
                imgTin.setImageResource(R.drawable.ic_favorite_black_24dp);
                imgtym.setImageResource(R.drawable.ic_favorite_black_24dp);
                MainActivity.arrayListCart.add(sp);
                Toast.makeText(this, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
            }

            // lưu vào share
            MainActivity.preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
            MainActivity.editor1 = MainActivity.preferences1.edit();
            String json = gson.toJson(MainActivity.arrayListCart);
            MainActivity.editor1.putString("CartList", json);
            MainActivity.editor1.apply();

        }else {
            MainActivity.arrayListCart.add(sp);
            MainActivity.preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
            MainActivity.editor1 = MainActivity.preferences1.edit();
            String json = gson.toJson(MainActivity.arrayListCart);
            MainActivity.editor1.putString("CartList", json);
            MainActivity.editor1.apply();
            imgTin.setImageResource(R.drawable.ic_favorite_black_24dp);
            Toast.makeText(this, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();


        }
    }

    private void htSanPham(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray arrayData = object.getJSONArray("data");
                            for (int i = 0; i< arrayData.length(); i++){
                                JSONObject objectData = arrayData.getJSONObject(i);
                                idUser= objectData.getString("id_nguoi_dung");
                                String id = objectData.getString("id");
                                String anh = objectData.getString("hinh_anh");
                                String name = objectData.getString("tieu_de");
                                int number = objectData.getInt("gia");
                                String mota = objectData.getString("mo_ta");
                                Picasso.get().load(URL.urlAnh+anh).into(imgSP);
                                String gia = formatter.format(number);
                                sp = new SanPham(
                                        id, name, gia, anh, true
                                );
                                txtName.setText(name);
                                txtGia.setText(gia +" đ");
                                txtMota.setText(mota);
                            }
                            JSONArray arrayUser = object.getJSONArray("user");
                            for (int j = 0; j< arrayUser.length(); j++ ){
                                JSONObject objectUser = arrayUser.getJSONObject(j);
                                String user = objectUser.getString("user_name");
                                sdt = objectUser.getString("phone");
                                txtUser.setText(user);
                                String hinhanh = objectUser.getString("hinh_anh_user");
                                Picasso.get().load(URL.urlAnh+hinhanh).into(ANguoiDang);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("IDSP", idsp);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


//    private void kt(){
//        Gson gson = new Gson();
//         boolean isCheck = false;
//
//            for ( int i = 0; i < MainActivity.arrayListCart.size(); i++ ){
//                String id = MainActivity.arrayListCart.get(i).getId();
//                if ( id.equals(idsp)){
//                    MainActivity.arrayListCart.set(i,sp);
//                    isCheck = true;
//                    Toast.makeText(this, "Bạn đã bỏ theo dõi tin", Toast.LENGTH_SHORT).show();
//                    imgTin.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                    MainActivity.arrayListCart.remove(i);
//                    MainActivity.preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
//                    MainActivity.editor1 = MainActivity.preferences1.edit();
//                    String json1 = gson.toJson(MainActivity.arrayListCart);
//                    MainActivity.editor1.putString("CartList", json1);
//                    MainActivity.editor1.apply();
//
//                }
//
//            }
//
//            if ( isCheck == false ){
//                imgTin.setImageResource(R.drawable.ic_favorite_black_24dp);
//                MainActivity.arrayListCart.add(sp);
//                Toast.makeText(this, "Bạn đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
//            }
//
//            // lưu vào share
//            MainActivity.preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
//            MainActivity.editor1 = MainActivity.preferences1.edit();
//            String json = gson.toJson(MainActivity.arrayListCart);
//            MainActivity.editor1.putString("CartList", json);
//            MainActivity.editor1.apply();
//
//    }
}
