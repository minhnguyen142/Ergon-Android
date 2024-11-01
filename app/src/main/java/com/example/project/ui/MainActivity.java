package com.example.project.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.EnterSDT;
import com.example.project.R;

public class MainActivity extends AppCompatActivity {

    ImageView imgAvatar;
    ImageButton imgbtnFb, imgbtnGmail, imgbtnPhone, imgbtnLogin;
    TextView FacebookLoginText, GmailLoginText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgAvatar = findViewById(R.id.imgAvatar);
        imgbtnFb = findViewById(R.id.imgbtnFb);
        imgbtnGmail = findViewById(R.id.imgbtnGmail);
        imgbtnPhone = findViewById(R.id.imgbtnPhone);
        imgbtnLogin = findViewById(R.id.imgbtnLogin);
        FacebookLoginText = findViewById(R.id.FacebookLoginText);
        GmailLoginText = findViewById(R.id.GmailLoginText);

        // Xử lý sự kiện nhấn nút Facebook
        imgbtnFb.setOnClickListener(v -> openFacebookApp(MainActivity.this));
        FacebookLoginText.setOnClickListener(v -> openFacebookApp(MainActivity.this));

        // Xử lý sự kiện nhấn nút Gmail
        imgbtnGmail.setOnClickListener(v -> openGmailApp(MainActivity.this));
        GmailLoginText.setOnClickListener(v -> openGmailApp(MainActivity.this));
        imgbtnLogin.setOnClickListener(v ->{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        //Xử lý sự kiện nhấn nút Phone
        imgbtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnterSDT.class);
                startActivity(intent);
            }
        });
    }

    // Hàm mở ứng dụng Facebook hoặc trình duyệt web
    public void openFacebookApp(Context context) {
        String facebookUrl = "https://www.facebook.com/yourPageName";
        Intent intent;

        try {
            // Kiểm tra xem ứng dụng Facebook có được cài đặt không
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            // Mở ứng dụng Facebook với URI scheme
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookUrl));
        } catch (PackageManager.NameNotFoundException e) {
            // Nếu ứng dụng Facebook không được cài đặt, mở bằng trình duyệt
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        }

        context.startActivity(intent);
    }

    // Hàm mở ứng dụng Gmail hoặc trình duyệt web
    public void openGmailApp(Context context) {
        String gmailUrl = "https://mail.google.com/mail/u/0/#inbox";
        Intent intent;

        try {
            // Kiểm tra xem ứng dụng Gmail có được cài đặt không
            context.getPackageManager().getPackageInfo("com.google.android.gm", 0);
            // Mở ứng dụng Gmail với URI scheme
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("mailto:")); // Sử dụng mailto để mở Gmail
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (PackageManager.NameNotFoundException e) {
            // Nếu ứng dụng Gmail không được cài đặt, mở Gmail qua trình duyệt
            gmailUrl = "https://mail.google.com/mail/u/0/#inbox";
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(gmailUrl));
        }

        context.startActivity(intent);
    }
}