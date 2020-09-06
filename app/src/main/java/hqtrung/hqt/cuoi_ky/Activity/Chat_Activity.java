package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.ListChatAdapter;
import hqtrung.hqt.cuoi_ky.adapter.MessageAdapter;
import hqtrung.hqt.cuoi_ky.model.Chat;
import hqtrung.hqt.cuoi_ky.model.ListChat;
import hqtrung.hqt.cuoi_ky.model.Product;

public class Chat_Activity extends AppCompatActivity {
    private ImageView imgBackChat;
    private RecyclerView recyclerViewListChat;
    ListChatAdapter adapter;
    ArrayList<ListChat> arrayList;
    ArrayList<Product> arrayListList;

    DatabaseReference myRef;
    String id_nguoi_goi;

    String urlGetNameUer = URL.urlGetNameUser;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);

        id_nguoi_goi = MainActivity.preferences.getString("id", "");
        AnhXa();
    }

    private void AnhXa() {
        dialog = new ProgressDialog(this);
        imgBackChat = (ImageView) findViewById(R.id.backChat);
        recyclerViewListChat = (RecyclerView) findViewById(R.id.RecyclerviewListChat);
        recyclerViewListChat.setHasFixedSize(true);
        recyclerViewListChat.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));

        arrayListList = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference().child("Chat");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idreceiver = snapshot.child("receiver").getValue(String.class);
                    String idsender = snapshot.child("sender").getValue(String.class);
                    String mes = snapshot.child("message").getValue(String.class);
                    String idProduct = snapshot.child("idProduct").getValue(String.class);

                    Chat chat = new Chat(idsender, idreceiver, mes, idProduct);
                    if (chat.getGender().equals(id_nguoi_goi)){
                        arrayListList.add(new Product(chat.getReceiver(), chat.getIdProduct(), chat.getMessage(), chat.getReceiver()));
                    }
                    if (chat.getReceiver().equals(id_nguoi_goi)){
                        arrayListList.add(new Product(chat.getGender(), chat.getIdProduct(), chat.getMessage(), chat.getGender()));
                    }
                }

                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imgBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void readChats(){
        arrayList = new ArrayList<>();
        int count = arrayListList.size();
        //bỏ phần tử trùng
        for (int i = 0; i < count; i++)
        {
            for (int j = i + 1; j < count; j++)
            {
                if (arrayListList.get(i).getIdProduct().equals(arrayListList.get(j).getIdProduct()))
                {
                    arrayListList.set(i, arrayListList.get(j));
                    arrayListList.remove(j--);
                    count--;
                }
            }
        }

//        Toast.makeText(this, ""+arrayListList.toString(), Toast.LENGTH_SHORT).show();
//        work
        for (int i = 0; i < arrayListList.size(); i++){
            readNameUser(urlGetNameUer, arrayListList.get(i).getReceiver().toString(), arrayListList.get(i).getIdProduct().toString(), arrayListList.get(i).getMes().toString(),
                    arrayListList.get(i).getIdSender().toString());
        }




    }

    private void readNameUser(String url, String idUser, String idProduct, String lastMes, String idSender){
        dialog.setMessage("Đang load dữ liệu");
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("thanhcong")){
                                JSONArray array = jsonObject.getJSONArray("data");

                                JSONObject jsonObject1 = array.getJSONObject(0);
                                String name = jsonObject1.getString("user_name");
                                String hinh_anh_user = jsonObject1.getString("hinh_anh_user");
                                String phone = jsonObject1.getString("phone");

                                JSONArray array2 = jsonObject.getJSONArray("dataProduct");
                                JSONObject jsonObject2 = array2.getJSONObject(0);
                                String hinh_anh_sp = jsonObject2.getString("hinh_anh");
                                String nameProduct = jsonObject2.getString("tieu_de");
                                String idsp = jsonObject2.getString("id");


                                arrayList.add(new ListChat(name.toString(), nameProduct, hinh_anh_user.toString(), hinh_anh_sp, lastMes, idsp, idSender,phone));
                                adapter = new ListChatAdapter(Chat_Activity.this, arrayList);
                                recyclerViewListChat.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Chat_Activity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("idUser", idUser);
                param.put("idProduct", idProduct);
                return param;
            }
        };

        requestQueue.add(stringRequest);

    }
}
