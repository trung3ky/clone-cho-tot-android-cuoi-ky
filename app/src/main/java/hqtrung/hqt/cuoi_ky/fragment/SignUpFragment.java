package hqtrung.hqt.cuoi_ky.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.Activity_SanPham;
import hqtrung.hqt.cuoi_ky.Activity.AuthActivity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;

public class SignUpFragment extends Fragment {
    View view;
    TextInputLayout layoutUser, layoutPhone, layoutPass, layoutConfim;
    EditText edtUser, edtPhone, edtPass,edtConfim;
    Button btnSU;
    TextView txtSI;
    ProgressDialog progressDialog;

    String url = URL.urlDangKy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_sign_up, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        layoutUser = (TextInputLayout)  view.findViewById(R.id.LayoutUserSU);
        layoutPhone = (TextInputLayout) view.findViewById(R.id.LayoutPhoneSU);
        layoutPass = (TextInputLayout) view.findViewById(R.id.LayoutPassSU);
        layoutConfim = (TextInputLayout) view.findViewById(R.id.LayoutConfimSU);
        edtUser = (EditText) view.findViewById(R.id.EditTextUserSU);
        edtPhone = (EditText) view.findViewById(R.id.EditTextPhoneSU);
        edtPass = (EditText) view.findViewById(R.id.EditTextPassSU);
        edtConfim = (EditText) view.findViewById(R.id.EditTextConfimSU);
        btnSU = (Button) view.findViewById(R.id.buttonSignUp);
        txtSI = (TextView) view.findViewById(R.id.TextViewSignIn);

        progressDialog = new ProgressDialog(getActivity());

        txtSI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAuth,
                        new SignInFragment()).commit();
            }
        });

        btnSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()){
                    progressDialog.setTitle("Đang Chạy");
                    progressDialog.show();
                    SignUp(url);
                }
            }
        });
        edtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edtUser.getText().toString().isEmpty()){
                    layoutUser.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        edtConfim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtConfim.getText().toString().equals(edtPass.getText().toString())){
                    layoutConfim.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean Validate(){
        if (edtUser.getText().toString().isEmpty()){
            layoutUser.setErrorEnabled(true);
            layoutUser.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }
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
        if (edtConfim.getText().toString().isEmpty()){
            layoutConfim.setErrorEnabled(true);
            layoutConfim.setError("Vui lòng Xác nhận mật khẩu");
            return false;
        }
        return true;
    }

    private void SignUp(String Url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("thanhcong")){
                            Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getActivity(), AuthActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","lỗi" + error);
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> pram = new HashMap<>();
                pram.put("UserSU", edtUser.getText().toString().trim());
                pram.put("PhoneSU", edtPhone.getText().toString().trim());
                pram.put("PassSU", edtPass.getText().toString().trim());
                return pram;
            }
        };
        requestQueue.add(stringRequest);
    }
}
