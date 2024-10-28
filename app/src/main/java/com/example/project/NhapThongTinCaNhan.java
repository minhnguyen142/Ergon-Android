package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.model.Book;

public class NhapThongTinCaNhan extends AppCompatActivity {

    private EditText edtName;
    private Spinner spinnerGender, spinnerBirthYear;
    private Button btnContinueTTCN;
    ImageButton imgbtnBackTTCN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_thong_tin_ca_nhan);
        // Ánh xạ các view
        edtName = findViewById(R.id.edtName);
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerBirthYear = findViewById(R.id.spinnerBirthYear);
        btnContinueTTCN = findViewById(R.id.btnContinueTTCN);
        imgbtnBackTTCN = findViewById(R.id.imgbtnBackTTCN);

        // Thiết lập dữ liệu cho spinner Giới tính
        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);

        // Thiết lập dữ liệu cho spinner Năm sinh
        ArrayAdapter<CharSequence> adapterBirthYear = ArrayAdapter.createFromResource(this,
                R.array.birth_year_array, android.R.layout.simple_spinner_item);
        adapterBirthYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBirthYear.setAdapter(adapterBirthYear);

        // Sự kiện khi thay đổi dữ liệu
        View.OnFocusChangeListener formValidator = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                validateForm();
            }
        };

        imgbtnBackTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backTTCN = new Intent(NhapThongTinCaNhan.this, TaoPassWord.class);
                startActivity(backTTCN);
            }
        });

        // Xử lý khi nhấn nút Tiếp tục
        btnContinueTTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String gender = String.valueOf(spinnerGender.getTextDirection());
                String year = String.valueOf(spinnerBirthYear.getTextDirection());

                // Kiểm tra xem mã otp có nhập đủ hay không
                if (name.isEmpty() || gender.isEmpty() || year.isEmpty()) {
                    Toast.makeText(NhapThongTinCaNhan.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Chuyển sang màn hình đăng nhập nếu mã otp hợp lệ
                    Intent continueSDT = new Intent(NhapThongTinCaNhan.this, TaoPassWord.class);
                    startActivity(continueSDT);
                }
            }
        });

        edtName.setOnFocusChangeListener(formValidator);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateForm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        spinnerBirthYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                validateForm();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    // Hàm kiểm tra form và bật/tắt nút Tiếp tục
    private void validateForm() {
        String name = edtName.getText().toString();
        boolean isGenderSelected = spinnerGender.getSelectedItemPosition() != 0;
        boolean isYearSelected = spinnerBirthYear.getSelectedItemPosition() != 0;

        if (!name.isEmpty() && isGenderSelected && isYearSelected) {
            btnContinueTTCN.setEnabled(true);
            btnContinueTTCN.setBackgroundTintList(getResources().getColorStateList(R.color.button_enabled_color));
        } else {
            btnContinueTTCN.setEnabled(false);
            btnContinueTTCN.setBackgroundTintList(getResources().getColorStateList(R.color.button_disabled_color));
        }
    }
}