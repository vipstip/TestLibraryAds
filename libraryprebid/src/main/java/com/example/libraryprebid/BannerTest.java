package com.example.libraryprebid;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.addendum.AdViewUtils;
import org.prebid.mobile.addendum.PbFindSizeError;

public class BannerTest extends SetupPB {

    private int refresCount = 0;
    private AdUnit adUnit;
    private PublisherAdView amBanner;
    private SizeAd adsize;
    private Context context;
    private int autoRefresh = 30000;
    private ResultCode resultCode;
    private AdListeners adListeners;
    private PublisherAdRequest request;

    public void setMillisAutoRefres(int autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public int getRefresCount() {
        return refresCount;
    }


    public void setSize(int with, int height){
        this.adsize = new SizeAd(with,height);

    }


    public void setSize(BannerSize size) {
        if (size == BannerSize.BANNER){
            this.adsize = new SizeAd(300,250);
        }
        else if (size == BannerSize.FULL_BANNER){
            this.adsize = new SizeAd(320,50);
        }
    }

    public void setAdUnit(String adUnitID){
        this.adUnit = new BannerAdUnit(adUnitID,adsize.getWith(),adsize.getHeight());
    }

    public BannerTest(Context context){
        super();
        this.context = context;

    }


    private void setUpBanner(final FrameLayout adFrame){
        this.amBanner = new PublisherAdView(context);
        amBanner.setAdUnitId(Constants.DFP_ADUNIT_ID);
        amBanner.setAdSizes(new AdSize(adsize.getWith(),adsize.getHeight()));
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
        //region PrebidMobile Mobile API 1.0 usage
        adUnit.setAutoRefreshPeriodMillis(autoRefresh);
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                BannerTest.this.resultCode = resultCode;
                amBanner.loadAd(request);
                refresCount++;
            }
        });

    }

    public void loadAdHostCustum(FrameLayout layout){
        SetupPBCustum();
        setUpBanner(layout);
        loadBanner();
    }
    public void loadAdHostCustum(FrameLayout layout,PublisherAdRequest request){
        BannerTest.this.request = request;
        SetupPBCustum();
        setUpBanner(layout);
        loadBanner();
    }

    public void loadAdHostAppnexus(FrameLayout layout){
        SetupPBAppnexus();
        setUpBanner(layout);
        loadBanner();
    }

    public void loadAdHostAppnexus(FrameLayout layout, PublisherAdRequest request){
        BannerTest.this.request = request;
        SetupPBAppnexus();
        setUpBanner(layout);
        loadBanner();
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
