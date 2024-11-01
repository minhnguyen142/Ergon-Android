package com.example.project.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button signUpButton;
    private DatabaseReference databaseReference;
    private ImageButton btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Kết nối đến Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        usernameEditText = findViewById(R.id.editTextText3);
        passwordEditText = findViewById(R.id.editTextText4);
        signUpButton = findViewById(R.id.button2);
        btnBack = findViewById(R.id.back1);

        btnBack.setOnClickListener(v -> {
            finish();
        });
        // Xử lý khi nhấn nút Đăng ký
        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                createUserIdAndSave(username, password);
            } else {
                Toast.makeText(SignUpActivity.this, "Vui lòng nhập tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserIdAndSave(String username, String password) {
        // Kiểm tra ID người dùng mới
        checkUserIdAvailability(username, password, 1);
    }

    private void checkUserIdAvailability(String username, String password, int userNumber) {
        String userId = "user" + String.format("%02d", userNumber); // Tạo ID như user01, user02,...

        // Kiểm tra xem ID người dùng đã tồn tại hay chưa
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Nếu ID đã tồn tại, kiểm tra ID tiếp theo
                    checkUserIdAvailability(username, password, userNumber + 1);
                } else {
                    // Nếu ID chưa tồn tại, lưu thông tin người dùng
                    saveUserToDatabase(userId, username, password);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(SignUpActivity.this, "Lỗi khi kiểm tra ID người dùng.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserToDatabase(String userId, String username, String password) {
        // Lưu thông tin vào Firebase
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("password", password);
        databaseReference.child(userId).setValue(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Để không quay lại màn hình đăng ký
            } else {
                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
