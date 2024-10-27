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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashSet;
import java.util.Set;

public class EnterOTP extends AppCompatActivity {

    private EditText otpInput1, otpInput2, otpInput3, otpInput4, otpInput5;
    private Button btnContinueOTP;
    private ImageButton imgbtnBackOTP;
    private String verificationId; // Để lưu verificationId

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

        // Nhận verificationId từ intent
        verificationId = getIntent().getStringExtra("verificationId");

        // Thiết lập tự động chuyển ô khi nhập
        setupOTPInput(otpInput1, otpInput2);
        setupOTPInput(otpInput2, otpInput3);
        setupOTPInput(otpInput3, otpInput4);
        setupOTPInput(otpInput4, otpInput5);

        imgbtnBackOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backOTP = new Intent(EnterOTP.this, EnterSDT.class);
                startActivity(backOTP);
                finish(); // Kết thúc màn hình hiện tại
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinueOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otpNumber = otpInput1.getText().toString().trim() +
                        otpInput2.getText().toString().trim() +
                        otpInput3.getText().toString().trim() +
                        otpInput4.getText().toString().trim() +
                        otpInput5.getText().toString().trim();

                if (otpNumber.length() != 5) {
                    Toast.makeText(EnterOTP.this, "Vui lòng nhập đủ mã OTP", Toast.LENGTH_SHORT).show();
                } else {
                    // Xác minh mã OTP với Firebase
                    verifyOTPCode(verificationId, otpNumber);
                }
            }
        });
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

    private void verifyOTPCode(String verificationId, String otpCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpCode);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công, chuyển sang màn hình tiếp theo
                        Intent intent = new Intent(EnterOTP.this, TaoPassWord.class);
                        startActivity(intent);
                        finish(); // Kết thúc màn hình hiện tại
                    } else {
                        // Nếu OTP sai hoặc hết hạn
                        Toast.makeText(EnterOTP.this, "Xác thực OTP không thành công.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Hàm kiểm tra tính duy nhất của các số OTP
    private boolean areOTPNumbersUnique(String... otpNumbers) {
        Set<String> otpSet = new HashSet<>();
        for (String otp : otpNumbers) {
            if (!otpSet.add(otp)) {
                // Nếu không thể thêm một phần tử nào đó vào Set, nghĩa là có số trùng nhau
                return false;
            }
        }
        return true;
    }
}
