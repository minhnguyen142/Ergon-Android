package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project.model.Book;

public class TaoPassWord extends AppCompatActivity {

    Button btnContinuePassWord;
    ImageButton imgbtnBackPassword;
    EditText usernameEditText, passwordEditText, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_pass_word);
        btnContinuePassWord = findViewById(R.id.btnContinuePassWord);
        imgbtnBackPassword = findViewById(R.id.imgbtnBackPassword);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPassword);

        imgbtnBackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backPassWord = new Intent(TaoPassWord.this, EnterOTP.class);
                startActivity(backPassWord);
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinuePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirm = confirmPassword.getText().toString().trim();

                // Kiểm tra xem username, password, confirm có được nhập hay không
                if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(TaoPassWord.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(TaoPassWord.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirm)) {
                    Toast.makeText(TaoPassWord.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang màn hình nhập thông tin cá nhân nếu hợp lệ
                    Intent continueSDT = new Intent(TaoPassWord.this,  NhapThongTinCaNhan.class);
                    startActivity(continueSDT);
                }
            }
        });
    }
}