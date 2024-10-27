package com.example.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.CountryCode;
import com.example.project.R;

import java.util.List;

public class CountryCodeAdapter extends ArrayAdapter<CountryCode> {
    public CountryCodeAdapter(Context context, List<CountryCode> countries) {
        super(context, 0, countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CountryCode country = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country_spinner, parent, false);
        }

        ImageView countryFlag = convertView.findViewById(R.id.flagImage);
        TextView countryCode = convertView.findViewById(R.id.countryCode);

        countryFlag.setImageResource(country.getFlagImage());
        countryCode.setText(country.getCountryCode());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
