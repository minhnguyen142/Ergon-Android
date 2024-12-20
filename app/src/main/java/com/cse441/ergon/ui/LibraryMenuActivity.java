package com.cse441.ergon.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cse441.ergon.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class LibraryMenuActivity extends AppCompatActivity {
    LinearLayout share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_library_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        share = findViewById(R.id.shareLayout);


        share.setOnClickListener(v -> {
            showShareBottomSheet();
        });
    }

    private void showShareBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.share.getContext());
        bottomSheetDialog.setContentView(R.layout.activity_share_book);

        ImageView closeShareSheet = bottomSheetDialog.findViewById(R.id.closeShareSheet);
        if (closeShareSheet != null) {
            closeShareSheet.setOnClickListener(v -> bottomSheetDialog.dismiss());
        }
        bottomSheetDialog.show();
    }


}