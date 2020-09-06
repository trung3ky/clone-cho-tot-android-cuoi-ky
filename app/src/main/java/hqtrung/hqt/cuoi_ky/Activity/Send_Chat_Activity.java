package hqtrung.hqt.cuoi_ky.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hqtrung.hqt.cuoi_ky.R;
import hqtrung.hqt.cuoi_ky.URL;
import hqtrung.hqt.cuoi_ky.adapter.MessageAdapter;
import hqtrung.hqt.cuoi_ky.model.Chat;
import hqtrung.hqt.cuoi_ky.model.SanPham;
import hqtrung.hqt.cuoi_ky.model.TimKiem;

public class Send_Chat_Activity extends AppCompatActivity {

    private ImageView imgBack, imgUser, imgSanPham, imgPhone, imgSMS;
    private TextView txtNameUser, txtNameSP, txtGiaSP,txtmau1, txtmau2, txtmau3, txtmau4, txtmau5;
    private Button btnSend;
    private EditText edtMsg;
    private RecyclerView recyclerViewChat;
    ArrayList<Chat> arrayList;
    MessageAdapter adapter;

    String idsp, idnd, idSender, phone, nameUser, anhUser;
    String sdt;
    String urlSP = URL.urlChiTietSanPham;
    DecimalFormat formatter;
    public static String id_nguoi_goi;
    public static String id_nguoi_nhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__chat_);
        Intent intent = getIntent();
        idsp = intent.getStringExtra("idsp");
        idSender = intent.getStringExtra("idSender");
        phone = intent.getStringExtra("phone");
        anhUser = intent.getStringExtra("anhUser");
        nameUser = intent.getStringExtra("nameUser");

        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");

        id_nguoi_goi =  MainActivity.preferences.getString("id", "");

        AnhXa();
        htSanPham(urlSP);
    }

    private void AnhXa() {
        txtmau1 = (TextView) findViewById(R.id.mau1);
        txtmau2 = (TextView) findViewById(R.id.mau2);
        txtmau3 = (TextView) findViewById(R.id.mau3);
        txtmau4 = (TextView) findViewById(R.id.mau4);
        txtmau5 = (TextView) findViewById(R.id.mau5);
        imgBack = (ImageView) findViewById(R.id.sendChat);
        imgUser = (ImageView) findViewById(R.id.ImageViewChatUsser);
        imgSanPham = (ImageView) findViewById(R.id.AnhSanPham);
        txtNameUser = (TextView) findViewById(R.id.TextViewChatUser);
        txtNameSP = (TextView) findViewById(R.id.NameSanPham);
        txtGiaSP = (TextView) findViewById(R.id.GiaSanPham);
        imgPhone = (ImageView) findViewById(R.id.dienthoai);
        imgSMS = (ImageView) findViewById(R.id.sms);
        btnSend = (Button) findViewById(R.id.btn_send);
        edtMsg = (EditText) findViewById(R.id.EditTextMessage);
        recyclerViewChat = (RecyclerView) findViewById(R.id.RecyclerviewListMess);
        recyclerViewChat.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerViewChat.setLayoutManager(linearLayoutManager);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+sdt));
                startActivity(callIntent);
            }
        });
        imgSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent=new Intent(Intent.ACTION_SENDTO);
                smsIntent.setData(Uri.parse("sms:"+sdt));
                startActivity(smsIntent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = edtMsg.getText().toString();
                id_nguoi_nhan = idnd;
                if (!msg.equals("")){
                    SendMessage(id_nguoi_goi, id_nguoi_nhan, msg, idsp);
                }else{
                    Toast.makeText(Send_Chat_Activity.this, "Không thể gởi nội dung trống.", Toast.LENGTH_SHORT).show();
                }

                edtMsg.setText("");
            }
        });

        txtmau1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_nguoi_nhan = idnd;
                SendMessage(id_nguoi_goi, id_nguoi_nhan, txtmau1.getText().toString(), idsp);
            }
        });
        txtmau2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_nguoi_nhan = idnd;
                SendMessage(id_nguoi_goi, id_nguoi_nhan, txtmau2.getText().toString(), idsp);
            }
        });
        txtmau3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_nguoi_nhan = idnd;
                SendMessage(id_nguoi_goi, id_nguoi_nhan, txtmau3.getText().toString(), idsp);
            }
        });
        txtmau4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_nguoi_nhan = idnd;
                SendMessage(id_nguoi_goi, id_nguoi_nhan, txtmau4.getText().toString(), idsp);
            }
        });
        txtmau5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_nguoi_nhan = idnd;
                SendMessage(id_nguoi_goi, id_nguoi_nhan, txtmau5.getText().toString(), idsp);
            }
        });

    }

    private void SendMessage(String sender, String receiver, String message, String idsp){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        hashMap.put("idProduct", idsp);

        myRef.child("Chat").push().setValue(hashMap);

    }

    private void readMessage(final String myid, final String userid){
        arrayList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idreceiver = snapshot.child("receiver").getValue(String.class);
                    String idsender = snapshot.child("sender").getValue(String.class);
                    String mes = snapshot.child("message").getValue(String.class);
                    String idProduct = snapshot.child("idProduct").getValue(String.class);

                    Chat chat = new Chat(idsender, idreceiver, mes, idProduct);
                    if ((chat.getReceiver().equals(myid) && chat.getGender().equals(userid) && chat.getIdProduct().equals(idsp)) ||
                            (chat.getReceiver().equals(userid) && chat.getGender().equals(myid) && chat.getIdProduct().equals(idsp))) {
                        arrayList.add(chat);
                    }
                    adapter = new MessageAdapter(Send_Chat_Activity.this, arrayList);
                    recyclerViewChat.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void htSanPham(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray arrayData = object.getJSONArray("data");
                            for (int i = 0; i< arrayData.length(); i++){
                                JSONObject objectData = arrayData.getJSONObject(i);
                                idnd = objectData.getString("id_nguoi_dung");

                                //hàm hiển thị nội dung chat


                                String id = objectData.getString("id");
                                String anh = objectData.getString("hinh_anh");
                                String name = objectData.getString("tieu_de");
                                int number = objectData.getInt("gia");
                                Picasso.get().load(URL.urlAnh+anh).into(imgSanPham);
                                String gia = formatter.format(number);

                                txtNameSP.setText(name);
                                txtGiaSP.setText(gia +" đ");
                            }
                            if (idnd.equals(id_nguoi_goi)){
                                sdt = phone;
                                txtNameUser.setText(nameUser);
                                Picasso.get().load(URL.urlAnh + anhUser).into(imgUser);
                                idnd = idSender;
                                id_nguoi_nhan = idnd;
                                readMessage(id_nguoi_goi, id_nguoi_nhan);
                            }else {
                                id_nguoi_nhan = idnd;
                                readMessage(id_nguoi_goi, id_nguoi_nhan);

                                JSONArray arrayUser = object.getJSONArray("user");
                                for (int j = 0; j < arrayUser.length(); j++) {
                                    JSONObject objectUser = arrayUser.getJSONObject(j);
                                    String user = objectUser.getString("user_name");
                                    sdt = objectUser.getString("phone");
                                    txtNameUser.setText(user);
                                    String hinhanh = objectUser.getString("hinh_anh_user");
                                    Picasso.get().load(URL.urlAnh + hinhanh).into(imgUser);
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

                        Toast.makeText(Send_Chat_Activity.this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("IDSP", idsp);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
