package com.example.libraryprebid;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.Host;
import org.prebid.mobile.InterstitialAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.VideoInterstitialAdUnit;

public class InterstitialVideo {
    private int refreshCount = 0;
    private AdUnit adUnit;
    private ResultCode resultCode;
    private PublisherAdRequest request;
    private Context context;
    private int autoRefresh;
    private AdListeners adListeners;

    private PublisherInterstitialAd amInterstitial;


    public void loadAd() {
        setupPBInterstitial();
        setupAMInterstitial();
        loadInterstitial();
    }

    public void setMillisAutoRefres(int autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    private void setupPBInterstitial() {
//        PrebidMobile.setPrebidServerHost(Host.APPNEXUS);
//        PrebidMobile.setPrebidServerAccountId(Constants.PBS_ACCOUNT_ID);
//
//        Host.CUSTOM.setHostUrl("https://s2s.valueimpression.com/openrtb2/auction");
//        PrebidMobile.setPrebidServerHost(Host.CUSTOM);
//        PrebidMobile.setPrebidServerAccountId("valueimpression_global");


        PrebidMobile.setPrebidServerHost(Host.RUBICON);
        PrebidMobile.setPrebidServerAccountId("1001");
        PrebidMobile.setStoredAuctionResponse("sample_video_response");
    }

    public void setAdUnit(String adUnitID){
        this.adUnit = new VideoInterstitialAdUnit(adUnitID);
    }

    public InterstitialVideo(Context context){
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
                InterstitialVideo.this.resultCode = resultCode;
                amInterstitial.loadAd(request);
                refreshCount++;
            }
        });
    }

    public void setAdlistenners(final AdListeners adlistenners){
        this.adListeners = adlistenners;
    }

    public void stopAutoRefresh() {
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
        }
    }

    public void startAutoRefresh(){
        loadInterstitial();
    }

    public void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }
}
