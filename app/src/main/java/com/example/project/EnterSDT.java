package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_book.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnterSDT extends AppCompatActivity {

    Button btnContinueSDT;
    ImageButton imgbtnBackSDT;
    EditText phoneNumberEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sdt);
        Spinner countryCodeSpinner = findViewById(R.id.countryCodeSpinner);
        btnContinueSDT = findViewById(R.id.btnContinueSDT);
        imgbtnBackSDT = findViewById(R.id.imgbtnBackSDT);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);

        List<CountryCode> countryCodes = new ArrayList<>();
        countryCodes.add(new CountryCode(R.drawable.usa, "+1"));
        countryCodes.add(new CountryCode(R.drawable.vietnam, "+84"));
        countryCodes.add(new CountryCode(R.drawable.france, "+33"));
        // Thêm các quốc gia khác vào đây

        CountryCodeAdapter adapter = new CountryCodeAdapter(this, countryCodes);
        countryCodeSpinner.setAdapter(adapter);

        imgbtnBackSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backSDT = new Intent(EnterSDT.this, MainActivity.class);
                startActivity(backSDT);
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinueSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                // Kiểm tra xem số điện thoại có được nhập hay không
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(EnterSDT.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    // Kiểm tra định dạng và độ dài số điện thoại
                    Toast.makeText(EnterSDT.this, "Số điện thoại không hợp lệ. Vui lòng nhập đủ 10 số.", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang màn hình EnterOTP nếu số điện thoại hợp lệ
                    Intent continueSDT = new Intent(EnterSDT.this, EnterOTP.class);
                    startActivity(continueSDT);
                }
            }
        });
    }

    // Hàm kiểm tra định dạng số điện thoại (đúng 10 chữ số)
    private boolean isValidPhoneNumber(String phoneNumberEditText){
        // Kiểm tra xem số điện thoại có 10 chữ số và chỉ bao gồm số
        String regex = "^[0-9]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumberEditText);
        return matcher.matches();
    }
}
