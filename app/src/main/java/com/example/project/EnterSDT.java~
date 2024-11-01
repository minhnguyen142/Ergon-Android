package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

    Button btnContinueSDT;
    ImageButton imgbtnBackSDT;
    EditText phoneNumberEditText;
    FirebaseAuth mAuth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sdt);

        Spinner countryCodeSpinner = findViewById(R.id.countryCodeSpinner);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        btnContinueSDT = findViewById(R.id.btnContinueSDT);
        imgbtnBackSDT = findViewById(R.id.imgbtnBackSDT);

        mAuth = FirebaseAuth.getInstance();

        List<CountryCode> countryCodes = new ArrayList<>();
        countryCodes.add(new CountryCode(R.drawable.usa, "+1"));
        countryCodes.add(new CountryCode(R.drawable.vietnam, "+84"));
        countryCodes.add(new CountryCode(R.drawable.france, "+33"));

        CountryCodeAdapter adapter = new CountryCodeAdapter(this, countryCodes);
        countryCodeSpinner.setAdapter(adapter);

        imgbtnBackSDT.setOnClickListener(v -> {
            Intent backSDT = new Intent(EnterSDT.this, MainActivity.class);
            startActivity(backSDT);
        });

        btnContinueSDT.setOnClickListener(v -> {
            String phoneNumber = phoneNumberEditText.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(EnterSDT.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            } else if (!isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(EnterSDT.this, "Số điện thoại không hợp lệ. Vui lòng nhập đủ 10 số.", Toast.LENGTH_SHORT).show();
            } else {
                checkPhoneNumberInFirebase(phoneNumber);
            }
        });
    }

    private void checkPhoneNumberInFirebase(String phoneNumber) {
        // Kiểm tra xem số điện thoại đã được đăng ký hay chưa
        mAuth.fetchSignInMethodsForEmail(phoneNumber + "@example.com")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> signInMethods = task.getResult().getSignInMethods();
                        if (signInMethods != null && !signInMethods.isEmpty()) {
                            // Số điện thoại đã tồn tại, chuyển sang màn hình đăng nhập
                            Toast.makeText(EnterSDT.this, "Số điện thoại đã tồn tại. Đang chuyển sang màn hình đăng nhập.", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(EnterSDT.this, Login.class);
                            startActivity(loginIntent);
                        } else {
                            // Số điện thoại chưa tồn tại, lưu số điện thoại và chuyển sang giao diện nhập thông tin bình thường
                            savePhoneNumberToFirebase(phoneNumber);
                        }
                    }
                });
    }

    private void savePhoneNumberToFirebase(String phoneNumber) {
        // Thực hiện các thao tác để lưu số điện thoại vào Firebase
        Toast.makeText(EnterSDT.this, "Số điện thoại mới, đang chuyển sang giao diện nhập thông tin.", Toast.LENGTH_SHORT).show();
        // Chuyển sang giao diện nhập thông tin
        Intent intent = new Intent(EnterSDT.this, NhapThongTinCaNhan.class);
        intent.putExtra("phoneNumber", phoneNumber); // Truyền số điện thoại sang màn hình nhập thông tin
        startActivity(intent);
    }

    private boolean isValidPhoneNumber(String phoneNumberEditText){
        // Kiểm tra xem số điện thoại có 10 chữ số và chỉ bao gồm số
        String regex = "^[0-9]{10}$";
        return phoneNumberEditText.matches(regex);
    }
}
