package hqtrung.hqt.cuoi_ky.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.fragment.Activity_More;

public class ThayDoiMatKhauActivity extends AppCompatActivity {

    ImageView imgBack;
    EditText edtmkc, edtmkm;
    Button btnXN;
    String pas;
    String iduser;
    String urlMatKhau = URL.urlMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thay_doi_mat_khau);
        Intent intent = getIntent();
        pas = intent.getStringExtra("mk");
        iduser = Activity_More.iduser;
        AnhXa();
    }

    private void AnhXa() {
        imgBack = (ImageView) findViewById(R.id.ImageViewBackMatKhaut);
        edtmkc = (EditText) findViewById(R.id.matkhaucu);
        edtmkm = (EditText) findViewById(R.id.matkhaumoi);
        btnXN = (Button) findViewById(R.id.ButtonCapNhatpass);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkc = edtmkc.getText().toString().trim();
                if (mkc.equals(pas)){
                    if (edtmkm.getText().length() > 6 ){
                        UpdateMatKhau(urlMatKhau);
                    }else {
                        Toast.makeText(ThayDoiMatKhauActivity.this, "mật khẩu ít nhất 7 ký tự", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ThayDoiMatKhauActivity.this, "mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void UpdateMatKhau(String url){
        RequestQueue requestQueue  = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong") == true){
                                startActivity(new Intent(ThayDoiMatKhauActivity.this, CaiDatTaiKhoanActivity.class));
                                Toast.makeText(ThayDoiMatKhauActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ThayDoiMatKhauActivity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > param = new HashMap<>();
                param.put("id",iduser);
                param.put("pass",edtmkm.getText().toString());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
