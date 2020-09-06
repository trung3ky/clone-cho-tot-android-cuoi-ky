package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.adapter.SanPhamAdapter;
import hqtrung.hqt.cuoi_ky.adapter.YeuThichAdapter;
import hqtrung.hqt.cuoi_ky.fragment.Acitivity_Add;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Home;
import hqtrung.hqt.cuoi_ky.fragment.Activity_More;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Notification;
import hqtrung.hqt.cuoi_ky.fragment.Activity_Sale;
import hqtrung.hqt.cuoi_ky.model.SanPham;

public class MainActivity extends AppCompatActivity {
    public static BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;


    public static ArrayList<SanPham> arrayListCart = new ArrayList<>();
    public static SharedPreferences preferences1;
    public static SharedPreferences.Editor editor1;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        User();
        them();
        kiemtraYeuThich();

        if (CaiDatTaiKhoanActivity.caidat.toString().equals("caidat")){
            CaiDatTaiKhoanActivity.caidat = "";
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Activity_More()).commit();
            bottomNavigationView.setSelectedItemId(R.id.page_4);
        }else {
            if (savedInstanceState ==  null){
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Activity_Home()).commit();
            }
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.page_1:
                        fragment = new Activity_Home();
                        break;
                    case R.id.page_2:
                        fragment = new Activity_Sale();
                        break;
                    case R.id.page_add:
                        fragment = new Activity_Home();
                        Intent intent = new Intent(getApplication(), AddActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.page_3:
                        fragment = new Activity_Notification();
                        break;
                    case R.id.page_4:
                        fragment = new Activity_More();
                        break;
                }
                fragmentTransaction.replace(R.id.FrameLayout, fragment);
                fragmentTransaction.commit();
                return true;
            }
        });
    }

    private void AnhXa() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBottom);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });
    }

    private void User(){

        Intent intent = getIntent();
        String user = intent.getStringExtra("user_name");
        String phone = intent.getStringExtra("phone");
        String id = intent.getStringExtra("id");
        String pass = intent.getStringExtra("pass");
        String hinhanh = intent.getStringExtra("hinhanh");

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();

        if (user == null && phone ==null){
            user = MainActivity.preferences.getString("user_name", "");
            id = MainActivity.preferences.getString("id", "");
            phone = MainActivity.preferences.getString("phone", "");
            pass = MainActivity.preferences.getString("pass", "");
            hinhanh = MainActivity.preferences.getString("hinhanh", "");

        }else{
            editor.putString("id", id);
            editor.putString("user_name", user);
            editor.putString("phone",phone);
            editor.putString("pass", pass);
            editor.putString("hinhanh", hinhanh);
            editor.apply();
        }

    }


    private void them(){

        preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        editor1 = preferences1.edit();
        Gson gson = new Gson();
        if ( MainActivity.arrayListCart.size() > 0  ){
            String json = gson.toJson(MainActivity.arrayListCart);
            editor1.putString("CartList", json);
            editor1.apply();
        }else {
            String json = MainActivity.preferences1.getString("CartList", null);
            editor1.putString("CartList", json);
            editor1.apply();
        }

    }


    private void kiemtraYeuThich(){
        preferences1 = getSharedPreferences("arrCart", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = MainActivity.preferences1.getString("CartList", null);

        if (json != null){
            try {
                JSONArray jsonArray = new JSONArray(json);
                MainActivity.arrayListCart.clear();

//                Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
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

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
//        else {
//            Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn thoát ứng dụng không?");
        builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
