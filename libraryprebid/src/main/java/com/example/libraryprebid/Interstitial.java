package com.example.libraryprebid;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.InterstitialAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.VideoInterstitialAdUnit;

class Interstitial extends SetupPB {
    private int refreshCount = 0;
    private AdUnit adUnit;
    private ResultCode resultCode;
    private PublisherAdRequest request;
    private Context context;
    private int autoRefresh;
    private SizeAd adsize;
    private AdListeners adListeners;
    private TypeAd typeAd;
    private PublisherInterstitialAd amInterstitial;


    void loadAd() {
        SetupPBCustum();
        setupAMInterstitial();
        loadInterstitial();
    }

    void setTypeAd(TypeAd typeAd) {
        this.typeAd = typeAd;
    }

    void setMillisAutoRefres(int autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    void setSize(int with, int height){
        this.adsize = new SizeAd(with,height);
    }

    void setAdUnit(String adUnitID){
        if (adsize != null && typeAd != TypeAd.INTERSTITIALSVIDEO)
        {
            this.adUnit = new InterstitialAdUnit(adUnitID,adsize.getWith(),adsize.getHeight());
        }
        else if (typeAd == TypeAd.INTERSTITIALSVIDEO){
            this.adUnit = new VideoInterstitialAdUnit(adUnitID);
        }
        else if(typeAd == TypeAd.INTERSTITIALS)
        this.adUnit = new InterstitialAdUnit(adUnitID);
    }

    Interstitial(Context context){
        super();
        this.context = context;
    };

    private void setupAMInterstitial() {
        this.amInterstitial = new PublisherInterstitialAd(context);
        amInterstitial.setAdUnitId(Constants.DFP_ADUNIT_ID);
        amInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                amInterstitial.show();
                adUnit.stopAutoRefresh();
                adListeners.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                adListeners.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                adListeners.onAdClosed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                adListeners.onAdImpression();
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                adListeners.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                adListeners.onAdOpened();
            }


            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adUnit.stopAutoRefresh();
                loadInterstitial();
                adListeners.onAdFailedToLoad(i);
            }
        });
    }
    private void loadInterstitial() {
        adUnit.setAutoRefreshPeriodMillis(autoRefresh);
        PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                Interstitial.this.resultCode = resultCode;
                amInterstitial.loadAd(request);
                refreshCount++;
            }
        });
    }

    void setAdlistenners(final AdListeners adlistenners){
        this.adListeners = adlistenners;
    }

    void stopAutoRefresh() {
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
        }
    }

    void startAutoRefresh(){
        loadInterstitial();
    }

    void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }
}
