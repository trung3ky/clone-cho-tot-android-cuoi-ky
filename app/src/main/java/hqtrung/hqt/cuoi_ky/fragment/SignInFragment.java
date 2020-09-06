package hqtrung.hqt.cuoi_ky.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Trace;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;

public class SignInFragment extends Fragment {
    View view;
    TextInputLayout layoutPhone, layoutPass;
    EditText edtPhone, edtPass;
    Button btnSI;
    TextView txtSU;
    ProgressDialog progressDialog;

    String url = URL.urlDangNhap;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_in, container, false);

//        String user = MainActivity.preferences.getString("user","");

            AnhXa();



        return view;
    }


    private void AnhXa() {
        layoutPhone = (TextInputLayout) view.findViewById(R.id.LayoutPhoneSI);
        layoutPass = (TextInputLayout) view.findViewById(R.id.LayoutPassSI);
        edtPhone = (EditText) view.findViewById(R.id.EditTextPhoneSI);
        edtPass = (EditText) view.findViewById(R.id.EditTextPassSI);
        btnSI = (Button) view.findViewById(R.id.buttonSignIn);
        txtSU = (TextView) view.findViewById(R.id.TextViewSignUp);
        progressDialog = new ProgressDialog(getActivity());

        txtSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAuth,
                        new SignUpFragment()).commit();
            }
        });

        btnSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    progressDialog.setTitle("Đang chạy");
                    progressDialog.show();
                    SignIn(url);
                }
            }
        });

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtPhone.getText().toString().isEmpty()){
                    layoutPhone.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtPass.getText().toString().length() > 7){
                    layoutPass.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean validate(){
        if (edtPhone.getText().toString().isEmpty()){
            layoutPhone.setErrorEnabled(true);
            layoutPhone.setError("Vui lòng nhập số điện thoại");
            return false;
        }
        if (edtPass.getText().toString().isEmpty()){
            layoutPass.setErrorEnabled(true);
            layoutPass.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        return true;
    }

    private void SignIn(String Url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getBoolean("thanhcong")){
                                JSONObject jsonObject = object.getJSONObject("data");
                                progressDialog.dismiss();

                                String id = jsonObject.getString("id");
                                String user_name = jsonObject.getString("user_name");
                                String pass = jsonObject.getString("pass");
                                String phone = jsonObject.getString("phone");
                                String hinhanh = jsonObject.getString("hinh_anh_user");
//                                Toast.makeText(getActivity(), "thanh cong" + phone+user_name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("id", id);
                                intent.putExtra("user_name", user_name);
                                intent.putExtra("phone", phone);
                                intent.putExtra("pass", pass);
                                intent.putExtra("hinhanh", hinhanh);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","lỗi"+error);
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pram = new HashMap<>();
                pram.put("PhoneSI", edtPhone.getText().toString());
                pram.put("PassSI", edtPass.getText().toString());
                return pram;
            }
        };
        requestQueue.add(stringRequest);
    }
}
