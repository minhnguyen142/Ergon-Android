package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btl_book.R;
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


        // Firebase Auth instance
        mAuth = FirebaseAuth.getInstance();

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

        btnContinueSDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                // Kiểm tra số điện thoại có hợp lệ không
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(EnterSDT.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(EnterSDT.this, "Số điện thoại không hợp lệ. Vui lòng nhập đủ 10 số.", Toast.LENGTH_SHORT).show();
                } else {
                    // Gửi OTP tới số điện thoại qua Firebase Authentication
                    sendVerificationCode(phoneNumber);
                }
            }
        });
    }

    private void sendVerificationCode(String phoneNumber) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)        // Số điện thoại cần gửi OTP
                .setTimeout(60L, TimeUnit.SECONDS)  // Thời gian chờ OTP (tính bằng giây)
                .setActivity(this)                  // Activity khởi tạo việc xác thực
                .setCallbacks(mCallbacks)           // Xử lý sự kiện khi OTP được gửi
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
                @Override
                public void onVerificationCompleted(PhoneAuthCredential credential) {
                    // Nếu OTP được tự động xác thực thành công
                    signInWithPhoneAuthCredential(credential);
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(EnterSDT.this, "Xác thực thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                    super.onCodeSent(verificationId, token);
                    // Truyền phoneNumber chính xác từ bên ngoài callback
                    Intent intent = new Intent(EnterSDT.this, EnterOTP.class);
                    intent.putExtra("phoneNumber", phoneNumber); // Truyền số điện thoại sang màn hình OTP
                    intent.putExtra("verificationId", verificationId); // Truyền verificationId
                    startActivity(intent);
                }
            };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        Toast.makeText(EnterSDT.this, "Xác thực thành công!", Toast.LENGTH_SHORT).show();
                        // Chuyển sang màn hình chính hoặc trang đăng nhập
                        Intent intent = new Intent(EnterSDT.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Đóng màn hình hiện tại
                    } else {
                        // Xác thực thất bại
                        Toast.makeText(EnterSDT.this, "Xác thực không thành công.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    // Kiểm tra số điện thoại trong Firebase
    private void checkPhoneNumberInFirebase(String phoneNumber) {
        // Kiểm tra xem số điện thoại đã được đăng ký hay chưa
        mAuth.fetchSignInMethodsForEmail(phoneNumber + "@example.com") // Tạo email ảo từ số điện thoại để kiểm tra
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<String> signInMethods = task.getResult().getSignInMethods();
                        if (signInMethods != null && !signInMethods.isEmpty()) {
                            // Số điện thoại đã tồn tại, chuyển sang màn hình đăng nhập
                            Toast.makeText(EnterSDT.this, "Số điện thoại đã tồn tại. Đang chuyển sang màn hình đăng nhập.", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(EnterSDT.this, Login.class);
                            startActivity(loginIntent);
                        } else {
                            // Số điện thoại chưa tồn tại, gửi mã OTP
                            sendOTP(phoneNumber);
                        }
                    }
                });
    }

    // Gửi mã OTP qua Firebase
    private void sendOTP(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Số điện thoại cần xác thực
                        .setTimeout(60L, TimeUnit.SECONDS) // Thời gian timeout
                        .setActivity(this)                 // Activity hiện tại
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                                // Xác minh thành công, có thể dùng credential để đăng nhập
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                // Xác minh thất bại
                                Toast.makeText(EnterSDT.this, "Xác minh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                // Lưu verificationId để xác thực mã OTP sau
                                EnterSDT.this.verificationId = verificationId;
                                // Chuyển sang màn hình OTP
                                Intent otpIntent = new Intent(EnterSDT.this, EnterOTP.class);
                                otpIntent.putExtra("phoneNumber", phoneNumber);
                                otpIntent.putExtra("verificationId", verificationId);
                                startActivity(otpIntent);
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private boolean isValidPhoneNumber(String phoneNumberEditText){
        // Kiểm tra xem số điện thoại có 10 chữ số và chỉ bao gồm số
        String regex = "^[0-9]{10}$";
        return phoneNumberEditText.matches(regex);
    }
}
