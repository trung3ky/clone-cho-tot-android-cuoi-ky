package hqtrung.hqt.cuoi_ky.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import hqtrung.hqt.cuoi_ky.R;

public class Add_Info_SanPham extends Fragment {
    EditText edtTD, edtMT, edtGia;
    Button btnXN;
    ImageView imgback;

    public static String TieuDe;
    public static String MoTa;
    public static String Gia;

    String originalString;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.add_info_sanpham, container, false);
        AnhXa();
        return view;
    }

    private void AnhXa() {
        edtTD = (EditText) view.findViewById(R.id.edtTieuDe);
        edtMT = (EditText) view.findViewById(R.id.edtMoTa);
        edtGia = (EditText) view.findViewById(R.id.edtGia);
        btnXN = (Button) view.findViewById(R.id.btnXacNhanInfo);
        imgback= (ImageView) view.findViewById(R.id.ImageViewAddTTSP);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Add_Chon_Anh();
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.FrameLayoutAdd, fragment).addToBackStack(null)
                        .commit();
            }
        });

        edtGia.addTextChangedListener(onTextChangedListener());

        btnXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTD.getText().toString().trim().isEmpty() || edtMT.getText().toString().trim().isEmpty() || edtGia.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(), "Vui  lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    TieuDe = edtTD.getText().toString().trim();
                    MoTa = edtMT.getText().toString().trim();
                    Gia = originalString;
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayoutAdd,
                            new Upload_SanPham()).addToBackStack(null).commit();

                }
            }
        });
    }

    private TextWatcher onTextChangedListener(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtGia.removeTextChangedListener(this);
                try {
                    originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    edtGia.setText(formattedString);
                    edtGia.setSelection(edtGia.getText().length());
                }catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtGia.addTextChangedListener(this);
            }
        };
    }
}
