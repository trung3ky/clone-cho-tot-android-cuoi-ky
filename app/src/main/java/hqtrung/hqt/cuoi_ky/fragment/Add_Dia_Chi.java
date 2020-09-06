package hqtrung.hqt.cuoi_ky.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
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
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.AddPhuongXaAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddQuanHuyenAdapter;
import hqtrung.hqt.cuoi_ky.adapter.AddTinhThanhAdapter;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.model.CacTinhThanh;
import hqtrung.hqt.cuoi_ky.model.DanhMuc;
import hqtrung.hqt.cuoi_ky.model.LoaiDanhMuc;

public class Add_Dia_Chi extends Fragment {
    View view;
    TextView txtTT, txtQH, txtPX;
    Button btnXN;

    ImageView imgBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.add_dia_chi, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        txtTT = (TextView) view.findViewById(R.id.TinhThanh);
        txtQH = (TextView) view.findViewById(R.id.QuanHuyen);
        txtPX = (TextView) view.findViewById(R.id.PhuongXa);
        btnXN = (Button) view.findViewById(R.id.buttonXacNhanDC);
        imgBack = (ImageView) view.findViewById(R.id.ImageViewAddDC);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_chon_Loai_Dang_Tin();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        txtTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                        new Add_Chon_Tinh_Thanh()).addToBackStack(null).commit();

            }
        });
        String ten = AddTinhThanhAdapter.ten;
        String id = AddTinhThanhAdapter.id;
            if (ten != null){
                txtTT.setText(ten);
                txtQH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new Add_chon_quan_huyen();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("id1", id);
                        fragment.setArguments(bundle1);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                                fragment).addToBackStack(null).commit();
                    }
                });
                String ten1 = AddQuanHuyenAdapter.ten1;
                String id1 = AddQuanHuyenAdapter.id1;
                if (ten1 != null){
                    txtQH.setText(ten1);
                    txtPX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragment = new Add_chon_phuong_xa();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("id2", id1);
                            fragment.setArguments(bundle1);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                                    fragment).addToBackStack(null).commit();
                        }
                    });
                    String ten3 = AddPhuongXaAdapter.ten3;
                    if (ten3 != null){
                        txtPX.setText(ten3);
                        btnXN.setVisibility(View.VISIBLE);
                        btnXN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                                        new Add_Chon_Anh()).addToBackStack(null).commit();
                            }
                        });
                    }
                }


            }
        }




    }



