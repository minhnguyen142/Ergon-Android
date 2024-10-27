package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.adapter.CountryCodeAdapter;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EnterSDT extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private Button btnContinueSDT;
    private Spinner countryCodeSpinner;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sdt);

        // Khởi tạo các view
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        btnContinueSDT = findViewById(R.id.btnContinueSDT);
        countryCodeSpinner = findViewById(R.id.countryCodeSpinner);

        // Khởi tạo Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Tạo danh sách quốc gia
        List<CountryCode> countries = new ArrayList<>();
        countries.add(new CountryCode(R.drawable.vietnam, "+84"));
        countries.add(new CountryCode(R.drawable.usa, "+1"));
        // Bạn có thể thêm các quốc gia khác ở đây nếu cần

        // Khởi tạo adapter
        CountryCodeAdapter adapter = new CountryCodeAdapter(this, countries);
        countryCodeSpinner.setAdapter(adapter);

        // Xử lý sự kiện khi nhấn nút Tiếp tục
        btnContinueSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = phoneNumberEditText.getText().toString().trim();
                String countryCode = ((CountryCode) countryCodeSpinner.getSelectedItem()).getCountryCode(); // Lấy mã quốc gia

                // Kiểm tra số điện thoại
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(EnterSDT.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gửi mã xác minh
                sendVerificationCode(countryCode + mobile);
            }
        });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build()
        );
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            String code = credential.getSmsCode();
            if (code != null) {
                navigateToEnterOTP(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(EnterSDT.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String verificationId,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            navigateToEnterOTP(verificationId);
        }
    };

    private void navigateToEnterOTP(String verificationId) {
        Intent intent = new Intent(EnterSDT.this, EnterOTP.class);
        intent.putExtra("verificationId", verificationId);
        startActivity(intent);
        finish();
    }
}
