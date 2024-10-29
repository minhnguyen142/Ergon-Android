package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaoPassWord extends AppCompatActivity {

    Button btnContinuePassWord;
    ImageButton imgbtnBackPassword;
    EditText usernameEditText, passwordEditText, confirmPassword;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_pass_word);

        btnContinuePassWord = findViewById(R.id.btnContinuePassWord);
        imgbtnBackPassword = findViewById(R.id.imgbtnBackPassword);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPassword);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Back button to previous screen
        imgbtnBackPassword.setOnClickListener(v -> {
            Intent backPassWord = new Intent(TaoPassWord.this, EnterSDT.class);
            startActivity(backPassWord);
        });

        // Handle Continue button
        btnContinuePassWord.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirm = confirmPassword.getText().toString().trim();
            String phoneNumber = getIntent().getStringExtra("phoneNumber");

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(TaoPassWord.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 8) {
                Toast.makeText(TaoPassWord.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirm)) {
                Toast.makeText(TaoPassWord.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            } else {
                // Save user details to Firebase
                saveUserToFirebase(phoneNumber, username, password);
            }
        });
    }

    private void saveUserToFirebase(String phoneNumber, String username, String password) {
        User user = new User(phoneNumber, username, password);

        databaseReference.child(phoneNumber).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(TaoPassWord.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TaoPassWord.this, Login.class);
                        intent.putExtra("phoneNumber", phoneNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TaoPassWord.this, "Lỗi: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
