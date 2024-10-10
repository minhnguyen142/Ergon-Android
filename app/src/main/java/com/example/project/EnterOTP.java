package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_book.R;

public class EnterOTP extends AppCompatActivity {

    private EditText otpInput1, otpInput2, otpInput3, otpInput4, otpInput5;
    Button btnContinueOTP;
    ImageButton imgbtnBackOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        btnContinueOTP = findViewById(R.id.btnContinueOTP);
        imgbtnBackOTP = findViewById(R.id.imgbtnBackOTP);

        // Kết nối với các EditText nhập mã OTP
        otpInput1 = findViewById(R.id.otpInput1);
        otpInput2 = findViewById(R.id.otpInput2);
        otpInput3 = findViewById(R.id.otpInput3);
        otpInput4 = findViewById(R.id.otpInput4);
        otpInput5 = findViewById(R.id.otpInput5);

        // Thiết lập tự động chuyển ô khi nhập
        setupOTPInput(otpInput1, otpInput2);
        setupOTPInput(otpInput2, otpInput3);
        setupOTPInput(otpInput3, otpInput4);
        setupOTPInput(otpInput4, otpInput5);
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

        imgbtnBackOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backOTP = new Intent(EnterOTP.this, EnterSDT.class);
                startActivity(backOTP);
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinueOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpNumber1 = otpInput1.getText().toString().trim();
                String otpNumber2 = otpInput2.getText().toString().trim();
                String otpNumber3 = otpInput3.getText().toString().trim();
                String otpNumber4 = otpInput4.getText().toString().trim();
                String otpNumber5 = otpInput5.getText().toString().trim();

                // Kiểm tra xem mã otp có nhập đủ hay không
                if (otpNumber1.isEmpty() || otpNumber2.isEmpty() || otpNumber3.isEmpty() || otpNumber4.isEmpty() || otpNumber5.isEmpty()) {
                    Toast.makeText(EnterOTP.this, "Vui lòng nhập đủ mã OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang màn hình đăng nhập nếu mã otp hợp lệ
                    Intent continueSDT = new Intent(EnterOTP.this, TaoPassWord.class);
                    startActivity(continueSDT);
                }
            }
        });
    }
}