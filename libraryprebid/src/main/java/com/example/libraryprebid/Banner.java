package com.example.libraryprebid;

import android.content.Context;

import android.widget.FrameLayout;

class Banner extends SetupPB{
    private int refresCount = 0;
    private AdUnits adUnits;
    private AdViewBanner amBanner;
    private SizeAd adsize;
    private Context context;
    private int autoRefresh = 30000;

    private AdListeners adListeners;
    private TypeAd typeAd;

    void loadAd(FrameLayout layout){
        SetupPBCustum();
        setUpBanner(layout);
        loadBanner();
    }


    void setTypeAd(TypeAd typeAd) {
        this.typeAd = typeAd;
    }

    void setMillisAutoRefres(int autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public int getRefresCount() {
        return refresCount;
    }

    void setSize(int with, int height){
        this.adsize = new SizeAd(with,height);
    }

    void setSize(BannerSize size){
        if (size == BannerSize.MEDIUM_RECTANGLE){
            this.adsize = new SizeAd(300,250);
        }
        else if (size == BannerSize.BANNER){
            this.adsize = new SizeAd(320,50);
        }
        else if (size == BannerSize.LARGE_BANNER){
            this.adsize = new SizeAd(320,100);
        }
        else if (size == BannerSize.FULL_BANNER){
            this.adsize = new SizeAd(468,60);
        }
        else if (size == BannerSize.LEADERBOARD){
            this.adsize = new SizeAd(728,90);
        }
    }

    void setAdUnit(String adUnitID){
        adUnits.setTypeAd(typeAd);
        adUnits.setAdUnit(adUnitID,adsize.getWith(), adsize.getHeight());
    }

    Banner(Context context){
        super();
        adUnits = new AdUnits();
        this.context = context;
    }


    private void setUpBanner(final FrameLayout adFrame){
        this.amBanner = new AdViewBanner(context);
        amBanner.setAdUnitId(Constants.DFP_ADUNIT_ID);
        amBanner.setAdSizes(adsize.getWith(),adsize.getHeight());
        adFrame.removeAllViews();
        adFrame.addView(amBanner.getPBAdview());

        amBanner.setAdListener(new AdListeners(){
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
                adUnits.setfindPrebidCreativeSize(amBanner);
                adListeners.onAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                adUnits.stopAutoRefresh();
                loadBanner();
                adListeners.onAdFailedToLoad(i);
            }
        });
    }

    private void loadBanner(){
        final Request request = new Request();
        request.CreateRequest();
        adUnits.setAutoRefreshPeriodMillis(autoRefresh);
        adUnits.fetchDemand(request.getRequest(), amBanner);
    }

    void setAdlistenners(final AdListeners adlistenners){
        this.adListeners = adlistenners;
    }

    void stopAutoRefresh() {
        adUnits.stopAutoRefresh();
    }

    void startAutoRefresh(){
        loadBanner();
    }

    void desTroy(){
        adUnits.desTroy();

    }

}
