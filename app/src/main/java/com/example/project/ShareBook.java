package com.example.project;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShareBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_book);
        LinearLayout facebookShare = findViewById(R.id.facebook_share);
        LinearLayout messengerShare = findViewById(R.id.messenger_share);
        LinearLayout copyLink = findViewById(R.id.copy_link);
        LinearLayout moreOptions = findViewById(R.id.more_options);


    }
}
