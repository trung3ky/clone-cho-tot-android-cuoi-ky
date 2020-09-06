package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.fragment.Activity_More;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class CaiDatTaiKhoanActivity extends AppCompatActivity {

    ImageView imgAnh, imgThayDoiAnh, imgBack;
    TextView txtName,txtSDT, txtDC, txtEmail, txtMK, txtGT, txtNS;

    TextView txtThang, txtNgay, txtNam, txtHuy, txtDongY;
    String DayOfMonth, Month, Year;

    String iduser;
    String urlUser = URL.urlUserCaiDat;
    String urlEmail = URL.urlEmail;
    String urlGioiTinh = URL.urlGioiTinh;
    String urlNgaySinh = URL.urlNgaySinh;
    String urlDiaChi = URL.urlCaiDatDiaChi;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String email, Diachi;
    Dialog dialog;
    String pas;
    String gt;
    String date = "";
    String date2;

    String urlanh;
    String nameanh;
    String upload = URL.upload;
    final int RESQUEST_CODE_CAMERA = 1;
    final int REQUEST_CODE_FOLDER = 2;

    public static String caidat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat_tai_khoan);

        iduser = Activity_More.iduser;
        AnhXa();

        User(urlUser);
    }

    private void AnhXa() {
        imgAnh = (ImageView) findViewById(R.id.CaiDatAnh);
        imgThayDoiAnh = (ImageView) findViewById(R.id.CaiDatThayDoiAnh);
        txtName = (TextView) findViewById(R.id.CaiDatName);
        txtDC = (TextView) findViewById(R.id.txtDiaChiuser);
        txtSDT = (TextView) findViewById(R.id.txtsodienthoai);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtMK = (TextView) findViewById(R.id.txtPass);
        txtGT = (TextView) findViewById(R.id.txtGioiTinh);
        txtNS = (TextView) findViewById(R.id.txtNgaySinh);
        imgBack = (ImageView) findViewById(R.id.ImageViewBackCaiDat);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CaiDatTaiKhoanActivity.this, MainActivity.class ));
                caidat = "caidat";
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(CaiDatTaiKhoanActivity.this);
                dialog.setContentView(R.layout.dong_email);


                EditText edtEmail = (EditText) dialog.findViewById(R.id.editTextEmail);
                Button btnLuu = (Button) dialog.findViewById(R.id.buttonLuuEmail);
                TextView txtEmail = (TextView) dialog.findViewById(R.id.TieuDeEmail);
                ImageView imgClose  = (ImageView) dialog.findViewById(R.id.closeEmail);

//                edtEmail.requestFocus();
//                InputMethodManager imgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imgr.showSoftInput(edtEmail, 0);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
                window.setAttributes(wlp);


                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                edtEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        txtEmail.setVisibility(View.GONE);
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (edtEmail.getText().length() > 0 ){
                            txtEmail.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtEmail.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(),"vui lòng nhập email",Toast.LENGTH_SHORT).show();
                        }else {
                            if (edtEmail.getText().toString().trim().matches(emailPattern)) {
                                Toast.makeText(getApplicationContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                email = edtEmail.getText().toString();
                                UpdateEmail(urlEmail);

                            } else {
                                Toast.makeText(getApplicationContext(),"địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                dialog.show();
            }
        });

        txtDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        txtMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaiDatTaiKhoanActivity.this, ThayDoiMatKhauActivity.class);
                intent.putExtra("mk", pas);
                startActivity(intent);
            }
        });
        txtGT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dailog();
            }
        });

        txtNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NgaySinh();
            }
        });

        imgThayDoiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DailogHinhAnh();
            }
        });

        txtDC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(CaiDatTaiKhoanActivity.this);
                dialog.setContentView(R.layout.dong_email);
                dialog.setCanceledOnTouchOutside(false);

                EditText edtEmail = (EditText) dialog.findViewById(R.id.editTextEmail);
                Button btnLuu = (Button) dialog.findViewById(R.id.buttonLuuEmail);
                TextView txtEmail = (TextView) dialog.findViewById(R.id.TieuDeEmail);
                ImageView imgClose  = (ImageView) dialog.findViewById(R.id.closeEmail);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();

//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
                wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
                window.setAttributes(wlp);

                imgClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                edtEmail.setHint("Nhập địa chỉ");
                edtEmail.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        txtEmail.setVisibility(View.GONE);
                        txtEmail.setText("Địa chỉ");
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (edtEmail.getText().length() > 0 ){
                            txtEmail.setVisibility(View.VISIBLE);
                            txtEmail.setText("Địa chỉ");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                btnLuu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edtEmail.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(),"vui lòng nhập email",Toast.LENGTH_SHORT).show();
                        }else {
                            Diachi = edtEmail.getText().toString();
                            UpdateDiaChi(urlDiaChi);
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void UpdateDiaChi(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(dialog.getContext(), CaiDatTaiKhoanActivity.class));
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Cập nhật địa chỉ thành công", Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                param.put("DiaChi", Diachi);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void NgaySinh(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleDateFormat.format(calendar.getTime());
                DayOfMonth = dayOfMonth+"";
                Month = month+"";
                Year = year+"";
                DailogNgaySinh();

            }
        },nam,thang, ngay);
        datePickerDialog.show();
    }

    private void UpdateNgaySinh(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(dialog.getContext(), CaiDatTaiKhoanActivity.class));
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        date = "";
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                param.put("date",date2);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void DailogNgaySinh(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_update_ngay_sinh);
        dialog.setCanceledOnTouchOutside(false);

        txtThang = (TextView) dialog.findViewById(R.id.NgaySinhThang);
        txtNgay = (TextView) dialog.findViewById(R.id.NgaySinhNgay);
        txtNam = (TextView) dialog.findViewById(R.id.NgaySinhNam);
        txtDongY = (TextView) dialog.findViewById(R.id.NgaySinhDongY);
        txtHuy = (TextView) dialog.findViewById(R.id.NgaySinhHuy);

        txtNgay.setText(DayOfMonth);
        txtThang.setText(Month);
        txtNam.setText(Year);

        date2 = txtNgay.getText().toString()+"/"+txtThang.getText().toString()+"/"+txtNam.getText().toString();




        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM; // vị trí
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;// trong suốt
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT; // rộng ngược lại
        window.setAttributes(wlp);

        txtDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateNgaySinh(urlNgaySinh);
            }
        });

        txtHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NgaySinh();
            }
        });
        txtNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NgaySinh();
            }
        });
        txtThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NgaySinh();
            }
        });

        dialog.show();
    }

    private void Dailog(){
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.chon_gioi_tinh);
        dialog.setCanceledOnTouchOutside(false);

        TextView txtNam = (TextView) dialog.findViewById(R.id.textViewNam);
        TextView txtNu = (TextView) dialog.findViewById(R.id.textViewNu);
        TextView txtKhac = (TextView) dialog.findViewById(R.id.textViewKhac);
        TextView txtHuy = (TextView) dialog.findViewById(R.id.textViewHUYGT);

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
        txtNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt = "nam";
                UpdateGioiTinh(urlGioiTinh);
            }
        });
        txtNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt = "nữ";
                UpdateGioiTinh(urlGioiTinh);
            }
        });
        txtKhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gt = "khác";
                UpdateGioiTinh(urlGioiTinh);
            }
        });


        dialog.show();
    }

    private void UpdateGioiTinh(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(dialog.getContext(), CaiDatTaiKhoanActivity.class));
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                param.put("gioitinh",gt);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void UpdateEmail(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(dialog.getContext(), CaiDatTaiKhoanActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                param.put("email",email);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void User(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
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
                                    String anh = URL.urlAnh+jsonObject.getString("hinh_anh_user");
                                    Picasso.get().load(anh).into(imgAnh);
                                    txtName.setText(jsonObject.getString("user_name"));
                                    txtSDT.setText(jsonObject.getString("phone"));
                                    pas = jsonObject.getString("pass");
                                    String email = jsonObject.getString("email");
                                    String DC = jsonObject.getString("diachi");
                                    String GT = jsonObject.getString("gioi_tinh");
                                    String NS = jsonObject.getString("ngay_thang_nam_sinh");
                                    if (!email.isEmpty()){
                                        txtEmail.setText(email);
                                        txtEmail.setTextColor(Color.BLACK);
                                    }
                                    if (!DC.isEmpty()){
                                        txtDC.setText(DC);
                                        txtDC.setTextColor(Color.BLACK);
                                    }
                                    if (!GT.isEmpty()){
                                        txtGT.setText(GT);
                                        txtGT.setTextColor(Color.BLACK);
                                    }
                                    if (!NS.isEmpty()){
                                        txtNS.setText(NS);
                                        txtNS.setTextColor(Color.BLACK);
                                    }
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
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void DailogHinhAnh(){
        dialog = new Dialog(this);
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
        if (requestCode == RESQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null ){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            urlanh = getStringImage(bitmap);
            nameanh = "user"+System.currentTimeMillis()+"";
            dialog.show();
            uploadAnh(upload);
            dialog.dismiss();
            imgAnh.setImageBitmap(bitmap);
        }
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri =data.getData();
            try {
                InputStream inputStream =getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                urlanh = getStringImage(bitmap);
                nameanh = "user"+System.currentTimeMillis()+"";
                dialog.show();
                uploadAnh(upload);
                dialog.dismiss();
                imgAnh.setImageBitmap(bitmap);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong")==true){
                                MainActivity.preferences = getSharedPreferences("user",MODE_PRIVATE);
                                MainActivity.editor = MainActivity.preferences.edit();
                                MainActivity.editor.putString("hinhanh", nameanh+".jpg");
                                MainActivity.editor.apply();
                                dialog.dismiss();
                                Toast.makeText(CaiDatTaiKhoanActivity.this, "Thay đổi ảnh thành công", Toast.LENGTH_SHORT).show();
                            }else{
                                dialog.dismiss();
                                Toast.makeText(CaiDatTaiKhoanActivity.this, "Thay đổi ảnh không thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(CaiDatTaiKhoanActivity.this, "lỗi hệ thống", Toast.LENGTH_SHORT).show();
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
