package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_book.R;

public class EnterOTP extends AppCompatActivity {

    private EditText otpInput1, otpInput2, otpInput3, otpInput4, otpInput5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        // Kết nối với các EditText nhập mã OTP
        otpInput1 = findViewById(R.id.otpInput1);
        otpInput2 = findViewById(R.id.otpInput2);
        otpInput3 = findViewById(R.id.otpInput3);
        otpInput4 = findViewById(R.id.otpInput4);

        // Thiết lập tự động chuyển ô khi nhập
        setupOTPInput(otpInput1, otpInput2);
        setupOTPInput(otpInput2, otpInput3);
        setupOTPInput(otpInput3, otpInput4);
    }

    private void setupOTPInput(final EditText currentInput, final EditText nextInput) {
        currentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 1) {
                    nextInput.requestFocus(); // Chuyển đến ô tiếp theo khi nhập xong
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}