package com.example.libraryprebid;

import android.content.Context;

import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.VideoAdUnit;
import org.prebid.mobile.addendum.AdViewUtils;
import org.prebid.mobile.addendum.PbFindSizeError;



public class Banner {
    private int refresCount = 0;

    private ResultCode resultCode;
    private AdUnit adUnit;

    private AdSize adsize;
    private Context context;
    private int autoRefresh = 30000;
    private String adUnitID;

    private AdListeners adListeners;
    private TypeAd typeAd;

    private PublisherAdView amBanner;
    private PublisherAdRequest request;

    private int CountFailedLoad = 0;

    public void loadAd(FrameLayout layout){
        stopAutoRefresh();
        CountFailedLoad = 0;
        SetupPB.getInstance();
        setAdUnit();
        setUpBanner(layout);
        loadBanner();
    }


    public void setTypeAd(TypeAd typeAd) {
        this.typeAd = typeAd;
    }

    public void setMillisAutoRefres(int autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public int getRefresCount() {
        return refresCount;
    }

    public void setSize(int with, int height){
        this.adsize = new AdSize(with,height);
    }

    public void setSize(AdSizes size){

        if (size == AdSizes.MEDIUM_RECTANGLE){
            this.adsize = AdSize.MEDIUM_RECTANGLE;
        }
        else if (size == AdSizes.BANNER){
            this.adsize = AdSize.BANNER;
        }
        else if (size == AdSizes.LARGE_BANNER){
            this.adsize = AdSize.LARGE_BANNER;
        }
        else if (size == AdSizes.FULL_BANNER){
            this.adsize = AdSize.FULL_BANNER;
        }
        else if (size == AdSizes.LEADERBOARD){
            this.adsize = AdSize.LEADERBOARD;
        }
        else if (size == AdSizes.SMART_BANNER){
            this.adsize = AdSize.SMART_BANNER;
        }

    }

    private void setAdUnit(){
        try {
            if (typeAd == TypeAd.VIDEO){
                this.adUnit = new VideoAdUnit(adUnitID, adsize.getWidth(), adsize.getHeight(), VideoAdUnit.PlacementType.IN_BANNER);
            }
            else if(typeAd == TypeAd.BANNER){
                this.adUnit = new BannerAdUnit(adUnitID,adsize.getWidth(), adsize.getHeight());
            }
            else
                this.adUnit = new BannerAdUnit(adUnitID,adsize.getWidth(), adsize.getHeight());
        } catch (Exception e) {
            if (adsize == null){
                Log.e("Err LoadAd","Size for banner NULL");
            }
        }
    }

    public void setAdUnitID(String adUnitID){
        this.adUnitID = adUnitID;
    }

    public Banner(Context context){
        this.context = context;
    }


    private void setUpBanner(final FrameLayout adFrame){
        this.amBanner = new PublisherAdView(context);
        amBanner.setAdUnitId(Constants.DFP_ADUNIT_ID);
        amBanner.setAdSizes(adsize);
        adFrame.removeAllViews();
        adFrame.addView(amBanner);

        amBanner.setAdListener(new AdListener(){
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
            public void onAdLoaded() {
                super.onAdLoaded();
                AdViewUtils.findPrebidCreativeSize(amBanner, new AdViewUtils.PbFindSizeListener() {
                    @Override
                    public void success(int width, int height) {
                        amBanner.setAdSizes(new AdSize(width, height));
                    }

                    @Override
                    public void failure(@NonNull PbFindSizeError error) {
                        Log.d("MyTag", "error: " + error);
                    }
                });
                Log.e("Loaded", "ok");
                CountFailedLoad = 0;
                adListeners.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                stopAutoRefresh();
                if (CountFailedLoad < 3){
                    loadBanner();
                    CountFailedLoad++;
                }
                Log.e("MyTag", "ok" + CountFailedLoad);
                adListeners.onAdFailedToLoad(i);
            }
        });
    }

    private void loadBanner(){
        final PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
        try {
            adUnit.setAutoRefreshPeriodMillis(autoRefresh);
            adUnit.fetchDemand(request, new OnCompleteListener() {
                @Override
                public void onComplete(ResultCode resultCode) {
                    Banner.this.resultCode = resultCode;
                    amBanner.loadAd(request);
                    refresCount++;
                }
            });
        } catch (Exception ignored) { }
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
        loadBanner();
    }

    public void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }

}
