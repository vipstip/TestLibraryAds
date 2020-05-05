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

    private AdListeners adListeners;
    private TypeAd typeAd;

    private PublisherAdView amBanner;
    private PublisherAdRequest request;


    public void loadAd(FrameLayout layout){
        SetupPB.getInstance();
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

    public void setSize(BannerSize size){

        if (size == BannerSize.MEDIUM_RECTANGLE){
            this.adsize = AdSize.MEDIUM_RECTANGLE;
        }
        else if (size == BannerSize.BANNER){
            this.adsize = AdSize.BANNER;
        }
        else if (size == BannerSize.LARGE_BANNER){
            this.adsize = AdSize.LARGE_BANNER;
        }
        else if (size == BannerSize.FULL_BANNER){
            this.adsize = AdSize.FULL_BANNER;
        }
        else if (size == BannerSize.LEADERBOARD){
            this.adsize = AdSize.LEADERBOARD;
        }
        else if (size == BannerSize.SMART_BANNER){
            this.adsize = AdSize.SMART_BANNER;
        }

    }

    public void setAdUnit(String adUnitID){
        if (typeAd == TypeAd.VIDEO){
            this.adUnit = new VideoAdUnit(adUnitID, adsize.getWidth(), adsize.getHeight(), VideoAdUnit.PlacementType.IN_BANNER);
        }
        else if(typeAd == TypeAd.BANNER){
            this.adUnit = new BannerAdUnit(adUnitID,adsize.getWidth(), adsize.getHeight());
        }
        else
            this.adUnit = new BannerAdUnit(adUnitID,adsize.getWidth(), adsize.getHeight());
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
                adListeners.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adUnit.stopAutoRefresh();
                loadBanner();
                adListeners.onAdFailedToLoad(i);
            }
        });
    }

    private void loadBanner(){
        final PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
        adUnit.setAutoRefreshPeriodMillis(autoRefresh);
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                Banner.this.resultCode = resultCode;
                amBanner.loadAd(request);
                refresCount++;
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
        loadBanner();
    }

    public void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }

}
