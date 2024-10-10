package com.example.project;

public class CountryCode {
    private int flagImage;
    private String countryCode;

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

