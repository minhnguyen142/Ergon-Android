package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl_book.R;

import java.util.List;

public class CountryCodeAdapter extends ArrayAdapter<CountryCode> {

    private Context context;
    private List<CountryCode> countryCodeList;

    public CountryCodeAdapter(Context context, List<CountryCode> countryCodeList) {
        super(context, 0, countryCodeList);
        this.context = context;
        this.countryCodeList = countryCodeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_country_spinner, parent, false);
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.flagImage);
        TextView textViewCountryCode = convertView.findViewById(R.id.countryCode);

        CountryCode currentItem = getItem(position);

        if (currentItem != null) {
            imageViewFlag.setImageResource(currentItem.getFlagImage());
            textViewCountryCode.setText(currentItem.getCountryCode());
        }

        return convertView;
    }
}

