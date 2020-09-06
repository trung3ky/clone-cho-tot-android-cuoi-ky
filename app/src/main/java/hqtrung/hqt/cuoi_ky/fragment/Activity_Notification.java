package hqtrung.hqt.cuoi_ky.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.Activity.Chat_Activity;
import hqtrung.hqt.cuoi_ky.Activity.MainActivity;
import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.adapter.LoaiAdapter;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.adapter.YeuThichAdapter;
import hqtrung.hqt.cuoi_ky.model.SanPham;


public class Activity_Notification extends Fragment {
    View view;
    ImageView imgChat;
    public  static TextView txtDSYT;
    RecyclerView recyclerViewYeuThich;
    YeuThichAdapter adapter;


//    public static SharedPreferences sharedPreferences;
//    public static SharedPreferences.Editor editor;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_notification, container, false);
        recyclerViewYeuThich = (RecyclerView) view.findViewById(R.id.RecyclerviewAddYeuThich);
        imgChat = (ImageView) view.findViewById(R.id.Chat);

        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Chat_Activity.class));
            }
        });

        txtDSYT = (TextView) view.findViewById(R.id.TextViewDanhSachYT);
//        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);




        Gson gson = new Gson();
        String json = MainActivity.preferences1.getString("CartList", null);

        if (json != null){
            try {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                recyclerViewYeuThich.setLayoutManager(linearLayoutManager);

                adapter = new YeuThichAdapter(getActivity(), MainActivity.arrayListCart);
                recyclerViewYeuThich.setAdapter(adapter);

                JSONArray jsonArray = new JSONArray(json);
                MainActivity.arrayListCart.clear();


//                MainActivity.editor1 = MainActivity.preferences1.edit();
//                MainActivity.editor1.clear();
//                MainActivity.editor1.apply();

                for ( int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject object = (JSONObject) jsonArray.get(i);
                    String name = object.getString("NameSP");
                    MainActivity.arrayListCart.add( new SanPham(
                            object.getString("id"),
                            object.getString("NameSP"),
                            object.getString("GiaSP"),
                            object.getString("AnhSP"),
                            true
                    ));
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            txtDSYT.setVisibility(View.VISIBLE);
        }

        if (MainActivity.arrayListCart.size()<1){
            txtDSYT.setVisibility(View.VISIBLE);
        }

        return view;
    }

}
