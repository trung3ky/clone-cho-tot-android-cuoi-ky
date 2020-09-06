package hqtrung.hqt.cuoi_ky.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddLoaiDanhMucAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddPhuongXaAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddQuanHuyenAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddTinhThanhAdapter;

public class Upload_SanPham extends Fragment {
    ProgressDialog dialog;
    EditText edtTD, edtMT, edtGia, edtDM, edtLDM, edtDangTin, edtTT, edtQH, edtPX;
    ImageView img, imgBack;
    Button btnXN;
    View view;

    String upload = URL.urlUploadSanPham;

    String tenDM = AddDanhMucAdapter.tenDM.toString();
    String tenLDM = AddLoaiDanhMucAdapter.tenLDM.toString();
    String LoaiDangTin = Add_chon_Loai_Dang_Tin.LoaDangTin.toString();
    String tenTT = AddTinhThanhAdapter.ten.toString();
    String tenQH = AddQuanHuyenAdapter.ten1.toString();
    String tenPX = AddPhuongXaAdapter.ten3.toString();
    String tieude = Add_Info_SanPham.TieuDe.toString();
    String mota = Add_Info_SanPham.MoTa.toString();
    String gia = Add_Info_SanPham.Gia.toString();
    Bitmap photo = Add_Chon_Anh.photo;

    String LDT =Add_chon_Loai_Dang_Tin.LoaDangTin1.toString();
    String idLDM = String.valueOf(AddLoaiDanhMucAdapter.id);
    String idDM = String.valueOf(AddDanhMucAdapter.id);

    String iduser = MainActivity.preferences.getString("id", "");

    String image = Add_Chon_Anh.image;
    String nameimage = Add_Chon_Anh.Nameimage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_upload_sanpham, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        imgBack = (ImageView) view.findViewById(R.id.ImageViewAddUploadSP);
        edtTD = (EditText) view.findViewById(R.id.edittextXNtieude);
        edtMT = (EditText) view.findViewById(R.id.edittextXNmota);
        edtGia = (EditText) view.findViewById(R.id.edittextXNgia);
        edtDM = (EditText) view.findViewById(R.id.edittextXNdanhmuc);
        edtLDM = (EditText) view.findViewById(R.id.edittextXNloaidanhmuc);
        edtDangTin = (EditText) view.findViewById(R.id.edittextXNdangtin);
        edtTT = (EditText) view.findViewById(R.id.edittextXNtinhthanh);
        edtQH = (EditText) view.findViewById(R.id.edittextXNquanhuyen);
        edtPX = (EditText) view.findViewById(R.id.edittextXNthixa);
        img = (ImageView) view.findViewById(R.id.imageviewXNanh);
        btnXN = (Button) view.findViewById(R.id.buttonXNupload);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_Info_SanPham();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        dialog = new ProgressDialog(getActivity());

        dialog.setMessage("Đang upload sản phẩm, làm ơn chờ");
        dialog.setCancelable(false);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        edtDM.setText(tenDM);
        edtLDM.setText(tenLDM);
        edtDangTin.setText(LoaiDangTin);
        edtTT.setText(tenTT);
        edtQH.setText(tenQH);
        edtPX.setText(tenPX);
        edtTD.setText(tieude);
        edtMT.setText(mota);
        edtGia.setText(gia);
        img.setImageBitmap(photo);

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                Upload(upload);
            }
        });
    }

    private void Upload(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("thanhcong")){
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }else{
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "lỗi hệ thống", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.d("AAA", "lỗi"+ error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("IDUser", iduser);
                param.put("TieuDe", tieude);
                param.put("MoTa", mota);
                param.put("Gia", gia);
                param.put("IDDanhMuc",idDM);
                param.put("IDLoaiDanhMuc", idLDM);
                param.put("DangTin", LDT);
                param.put("TinhThanh", tenTT);
                param.put("QuanHuyen", tenQH);
                param.put("PhuongXa", tenPX);
                param.put("Image", image);
                param.put("NameImage", nameimage);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
