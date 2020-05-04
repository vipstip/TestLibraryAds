package com.example.libraryprebid;

import android.content.Context;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

class AdViewBanner {

    private PublisherAdView amBanner;

    AdViewBanner(Context context){
        this.amBanner = new PublisherAdView(context);
    }

    void setAdUnitId(String DFP_ADUNIT_ID){
        amBanner.setAdUnitId(DFP_ADUNIT_ID);
    }

    void setAdSizes(int with,int height){
        amBanner.setAdSizes(new AdSize(with,height));
    }

    void setAdListener(AdListeners adListeners){
        amBanner.setAdListener(adListeners);
    }

    void loadAd(PublisherAdRequest request){
        amBanner.loadAd(request);
    }

    PublisherAdView getPBAdview(){
        return amBanner;
    }

}
