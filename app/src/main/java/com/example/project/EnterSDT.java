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
                Intent back = new Intent(EnterSDT.this, MainActivity.class);
                startActivity(back);
            }
        });

        // Theo dõi sự thay đổi của trường nhập số điện thoại
        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần làm gì trước khi thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Kiểm tra xem người dùng có nhập số điện thoại hay không
                if (s.length() > 0) {
                    btnContinueSDT.setVisibility(View.VISIBLE); // Hiển thị nút Tiếp tục nếu có nhập
                } else {
                    btnContinueSDT.setVisibility(View.GONE); // Ẩn nút Tiếp tục nếu không nhập
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần làm gì sau khi thay đổi
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinueSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                // Kiểm tra xem số điện thoại có được nhập hay không
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(EnterSDT.this, "Vui lòng nhập số điên thoại", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang màn hình EnterOTP nếu số điện thoại hợp lệ
                    Intent continueSDT = new Intent(EnterSDT.this, EnterOTP.class);
                    startActivity(continueSDT);
                }
            }
        });
    }
}