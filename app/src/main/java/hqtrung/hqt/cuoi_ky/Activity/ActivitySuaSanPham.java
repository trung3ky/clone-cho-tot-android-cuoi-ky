package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.DanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.QuanLyTinAdapter;
import hqtrung.hqt.cuoi_ky.adapter.UpdateDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.UpdateTinhThanhAdapter;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Sale;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class ActivitySuaSanPham extends AppCompatActivity {

    public static EditText edtTD, edtMT, edtGia, edtDM, edtLDM, edtDangTin, edtTT, edtQH, edtPX;
    ImageView img;
    Button btnXN;
    public static Dialog dialog;
    RecyclerView recyclerViewDM;
    UpdateDanhMucAdapter adapterDM;
    ArrayList<DanhMuc> arrayListDM;

    RecyclerView recyclerViewtt;
    ArrayList<CacTinhThanh> arrayListTT;
    UpdateTinhThanhAdapter adapterTT;


    String url = URL.urlAddDanhMuc;
    String urlUpdateSanPham = URL.urlUpdateSanPham;

    public int Request_Code_Image = 123;
    String id;
    String urlSP = URL.urlgetsanpham;

    String APITinhThanh = URL.ApiTinhThanh;
    String image;
    String Nameimage;

    String tieude, mota, gia, danhmuc, loaidanhmuc, loaidangtin, tinhthanh, quanhuyen, phuongxa;
    String iduser;
    ProgressDialog dialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);

        AnhXa();
    }


    private void AnhXa() {
        edtTD = (EditText) findViewById(R.id.edittextXNtieude1);
        edtMT = (EditText) findViewById(R.id.edittextXNmota1);
        edtGia = (EditText) findViewById(R.id.edittextXNgia1);
        edtDM = (EditText) findViewById(R.id.edittextXNdanhmuc1);
        edtLDM = (EditText) findViewById(R.id.edittextXNloaidanhmuc1);
        edtDangTin = (EditText) findViewById(R.id.edittextXNdangtin1);
        edtTT = (EditText) findViewById(R.id.edittextXNtinhthanh1);
        edtQH = (EditText) findViewById(R.id.edittextXNquanhuyen1);
        edtPX = (EditText) findViewById(R.id.edittextXNthixa1);
        img = (ImageView) findViewById(R.id.imageviewXNanh1);
        btnXN = (Button) findViewById(R.id.buttonXNupload1);
        dialog1 = new ProgressDialog(this);

        edtGia.addTextChangedListener(onTextChangedListener());

        edtDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
        edtLDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });

        edtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTinhThanh();
            }
        });
        edtQH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTinhThanh();
            }
        });
        edtPX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTinhThanh();
            }
        });

        edtDangTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoaiDangTin();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);

            }
        });

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tieude = edtTD.getText().toString();
                mota = edtMT.getText().toString();
                gia  = edtGia.getText().toString();
                danhmuc = edtDM.getText().toString();
                loaidanhmuc = edtLDM.getText().toString();
                loaidangtin = edtDangTin.getText().toString();
                tinhthanh = edtTT.getText().toString();
                quanhuyen = edtQH.getText().toString();
                phuongxa = edtPX.getText().toString();


                iduser = MainActivity.preferences.getString("id", "");

//                Toast.makeText(ActivitySuaSanPham.this, iduser+"/"+id+"/"+tieude+"/"+mota+"/"+gia+"/"+danhmuc+"/"+loaidanhmuc+"/"+loaidangtin+"/"+tinhthanh+"/"+quanhuyen+"/"+phuongxa+"/", Toast.LENGTH_LONG).show();
                Update(urlUpdateSanPham);
            }
        });

        id = QuanLyTinAdapter.idSanPham;

        GetSanPham(urlSP);
    }

    private void Update(String urlupdate){
        dialog1.setTitle("Đang xử lý");
        dialog1.setMessage("vui lòng đợi");
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlupdate,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object  = new JSONObject(response);
                            if (object.getBoolean("0") == true){
                                dialog1.dismiss();
                                Toast.makeText(ActivitySuaSanPham.this, "Bạn đã sửa tin thành công", Toast.LENGTH_SHORT).show();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Activity_Sale()).commit();
                                startActivity(new Intent(ActivitySuaSanPham.this, MainActivity.class));
                            }else{
                                dialog1.dismiss();
                                Toast.makeText(ActivitySuaSanPham.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ActivitySuaSanPham.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", id);
                param.put("iduser", iduser);
                param.put("tieude", tieude);
                param.put("mota", mota);
                param.put("gia", gia);
                param.put("danhmuc", danhmuc);
                param.put("loaidanhmuc", loaidanhmuc);
                param.put("loaidangtin", loaidangtin);
                param.put("tinhthanh", tinhthanh);
                param.put("quanhuyen", quanhuyen);
                param.put("phuongxa", phuongxa);
                param.put("image", image);
//                param.put("nameimage", Nameimage);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_Code_Image && resultCode ==  RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap photo = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(photo);
                image = getStringImage(photo);
                Nameimage = System.currentTimeMillis()+"";

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void DialogLoaiDangTin(){
        ImageView imageView;
        RadioButton rbMua, rbBan;
        dialog = new Dialog(ActivitySuaSanPham.this);
        dialog.setContentView(R.layout.add_loai_dang_tin);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        imageView = (ImageView) dialog.findViewById(R.id.ImageViewAddLDT);
        rbBan = (RadioButton) dialog.findViewById(R.id.radioBan);
        rbMua = (RadioButton) dialog.findViewById(R.id.radioMua);
        imageView.setVisibility(View.GONE);

        rbMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDangTin.setText("canmua");
                dialog.dismiss();
            }
        });
        rbBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtDangTin.setText("canban");
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void DialogTinhThanh(){
        dialog = new Dialog(ActivitySuaSanPham.this);
        dialog.setContentView(R.layout.dialog_add_tinh_thanh);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        recyclerViewtt = (RecyclerView) dialog.findViewById(R.id.RecyclerviewAddTinhThanh);
        arrayListTT =new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivitySuaSanPham.this, RecyclerView.VERTICAL, false);
        recyclerViewtt.setLayoutManager(linearLayoutManager);

        adapterTT = new UpdateTinhThanhAdapter(ActivitySuaSanPham.this, arrayListTT);
        recyclerViewtt.setAdapter(adapterTT);

        TinhThanh(APITinhThanh);

        dialog.show();
    }

    private void TinhThanh(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest  = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for(int i = 0; i< array.length(); i++){
                                JSONObject object1 = array.getJSONObject(i);
                                int id = object1.getInt("id");
                                String ten = object1.getString("name");
                                arrayListTT.add(new CacTinhThanh(
                                        ten, id
                                ));
                            }
                            adapterTT.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySuaSanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "lỗi" + error);
                    }
                });
        requestQueue.add(stringRequest);
    }

    private void ShowDialog(){
        ImageView imageView;
        dialog = new Dialog(ActivitySuaSanPham.this);
        dialog.setContentView(R.layout.add_danh_muc);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.TOP; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);

        imageView = (ImageView) dialog.findViewById(R.id.ImageViewAddDM);
        imageView.setVisibility(View.GONE);

        recyclerViewDM = (RecyclerView) dialog.findViewById(R.id.RecyclerviewAddDanhMuc);
        arrayListDM =new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivitySuaSanPham.this, RecyclerView.VERTICAL, false);
        recyclerViewDM.setLayoutManager(linearLayoutManager);

        adapterDM = new UpdateDanhMucAdapter(ActivitySuaSanPham.this, arrayListDM);
        recyclerViewDM.setAdapter(adapterDM);
        addDM(url);
        dialog.show();
    }

    private void addDM(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObjectt = response.getJSONObject(i);
                                arrayListDM.add(new DanhMuc(
                                        jsonObjectt.getInt("id"),
                                        jsonObjectt.getString("anh"),
                                        jsonObjectt.getString("ten_danh_muc")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterDM.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivitySuaSanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Lỗi"+error);
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetSanPham(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                JSONArray array = object.getJSONArray("data");
                                for(int i = 0 ; i < array.length(); i++){
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String tieude = jsonObject.getString("tieu_de");
                                    String mota = jsonObject.getString("mo_ta");
                                    String gia = jsonObject.getString("gia");
                                    image = jsonObject.getString("hinh_anh");
                                    String tinhthanh = jsonObject.getString("tinh_thanh");
                                    String quanhuyen = jsonObject.getString("quan_huyen");
                                    String phuongxa = jsonObject.getString("phuong_xa");
                                    String loai = jsonObject.getString("mua_or_ban");
                                    edtDangTin.setText(loai);
                                    edtTD.setText(tieude);
                                    edtMT.setText(mota);
                                    edtGia.setText(gia);
                                    Picasso.get().load(URL.urlAnh+image).into(img);
                                    edtTT.setText(tinhthanh);
                                    edtQH.setText(quanhuyen);
                                    edtPX.setText(phuongxa);
                                }
                                JSONArray array1 = object.getJSONArray("DM");
                                for (int i = 0 ; i< array1.length();i++){
                                    JSONObject jsonObject = array1.getJSONObject(i);
                                    String ten_danh_muc = jsonObject.getString("ten_danh_muc");
                                    edtDM.setText(ten_danh_muc);
                                }
                                JSONArray array2 = object.getJSONArray("LDM");
                                for (int i = 0 ; i< array2.length();i++){
                                    JSONObject jsonObject = array2.getJSONObject(i);
                                    String ten_loai_danh_muc = jsonObject.getString("ten_loai");
                                    edtLDM.setText(ten_loai_danh_muc);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActivitySuaSanPham.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idsp", id);
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private TextWatcher onTextChangedListener(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtGia.removeTextChangedListener(this);
                try {
                    String originalString = s.toString();

//                    Long longval;
//                    if (originalString.contains(",")) {
//                        originalString = originalString.replaceAll(",", "");
//                    }
//                    longval = Long.parseLong(originalString);

//                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                    formatter.applyPattern("#,###,###,###");
//                    String formattedString = formatter.format(longval);

                    edtGia.setText(originalString);
                    edtGia.setSelection(edtGia.getText().length());
                }catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtGia.addTextChangedListener(this);
            }
        };
    }

    public String getStringImage(Bitmap bm){
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100, ba);
        byte[] imagebyte = ba.toByteArray();
        String encode = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encode;
    }

}
