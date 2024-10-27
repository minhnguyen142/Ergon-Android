package com.example.project;

public class CountryCode {
    private int flagImage; // Hình ảnh cờ
    private String countryCode; // Mã quốc gia

    public CountryCode(int flagImage, String countryCode) {
        this.flagImage = flagImage;
        this.countryCode = countryCode;
    }

    public int getFlagImage() {
        return flagImage;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
