package com.example.project;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class EnterSDT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_sdt);
        Spinner countryCodeSpinner = findViewById(R.id.countryCodeSpinner);

        List<CountryCode> countryCodes = new ArrayList<>();
        countryCodes.add(new CountryCode(R.drawable.usa, "+1"));
        countryCodes.add(new CountryCode(R.drawable.vietnam, "+84"));
        countryCodes.add(new CountryCode(R.drawable.france, "+33"));
        // Thêm các quốc gia khác vào đây

        CountryCodeAdapter adapter = new CountryCodeAdapter(this, countryCodes);
        countryCodeSpinner.setAdapter(adapter);
    }
}