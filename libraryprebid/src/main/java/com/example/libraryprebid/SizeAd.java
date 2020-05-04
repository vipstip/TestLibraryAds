package com.example.libraryprebid;

import com.google.android.gms.ads.AdSize;

public class SizeAd {
    private int with;
    private int height;

    public SizeAd(int with, int height){
        this.with = with;
        this.height = height;
    }

    public int getWith() {
        return with;
    }

    public int getHeight() {
        return height;
    }
}
