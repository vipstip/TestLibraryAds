package com.example.libraryprebid;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.Host;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.PrebidMobile;
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
    private String placement = "";
    private AdListeners adListeners;
    private TypeAd typeAd = TypeAd.VIDEO;

    private PublisherAdView amBanner;
    private PublisherAdRequest request;

    private Constants constants;

    private FrameLayout layout;

    private int CountFailedLoad = 0;

    private CheckOnComplete checkOnComplete;
    public void loadAd(FrameLayout layout){
        stopAutoRefresh();
        CountFailedLoad = 0;
        this.layout = layout;
        if (constants.isOnCompleteConfig()){
            setUpConfigAndLoad();
        } else {
            checkOnComplete.start();
        }

    }

    private void setUpConfigAndLoad(){
        SetupPB();
        setAdUnit();
        setUpBanner(layout);
        loadBanner();
    }

    private void SetupPB(){
        Host.CUSTOM.setHostUrl("https://pb-server.vliplatform.com/openrtb2/auction");
        if (typeAd == TypeAd.VIDEO){
            PrebidMobile.setPrebidServerHost(constants.VD_HOST);
            PrebidMobile.setPrebidServerAccountId(constants.VD_PBS_ACCOUNT_ID_APPNEXUS);
            PrebidMobile.setStoredAuctionResponse(constants.VD_STORED_AUCTION_RESPONSE_CONFIG);
            Log.e("constants.VD_HOST",constants.VD_HOST + " ");
            Log.e("constants.VD_PBS_ACC",constants.VD_PBS_ACCOUNT_ID_APPNEXUS + " ");
            Log.e("constants.VD_STORED",constants.VD_STORED_AUCTION_RESPONSE_CONFIG + " ");
        } else  {
            PrebidMobile.setPrebidServerHost(constants.HOST);
            PrebidMobile.setPrebidServerAccountId(constants.PBS_ACCOUNT_ID_APPNEXUS);
            PrebidMobile.setStoredAuctionResponse(constants.STORED_AUCTION_RESPONSE_CONFIG);
        }
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

        switch (size) {
            case MEDIUM_RECTANGLE:
                this.adsize = AdSize.MEDIUM_RECTANGLE;
                break;
            case BANNER:
                this.adsize = AdSize.BANNER;
                break;
            case LARGE_BANNER:
                this.adsize = AdSize.LARGE_BANNER;
                break;
            case FULL_BANNER:
                this.adsize = AdSize.FULL_BANNER;
                break;
            case LEADERBOARD:
                this.adsize = AdSize.LEADERBOARD;
                break;
            case SMART_BANNER:
                this.adsize = AdSize.SMART_BANNER;
                break;
        }

    }

    private void setAdUnit(){
        try {
            if (typeAd == TypeAd.VIDEO) {
                // Prebid 1.6 update
//                VideoBaseAdUnit.Parameters parameters = new VideoBaseAdUnit.Parameters();
//                parameters.setMimes(Arrays.asList("video/mp4"));
//
//                parameters.setProtocols(Arrays.asList(Signals.Protocols.VAST_2_0));
//                // parameters.setProtocols(Arrays.asList(new Signals.Protocols(2)));
//
//                parameters.setPlaybackMethod(Arrays.asList(Signals.PlaybackMethod.AutoPlaySoundOff));
//                // parameters.setPlaybackMethod(Arrays.asList(new Signals.PlaybackMethod(2)));
//
//                parameters.setPlacement(Signals.Placement.InBanner);
//                // parameters.setPlacement(new Signals.Placement(2));
//
//                VideoAdUnit adUnit = new VideoAdUnit(constants.VD_PUB_ADUNIT_ID, adsize.getWidth(), adsize.getHeight());
//                adUnit.setParameters(parameters);
//                this.adUnit = adUnit;
                this.adUnit = new VideoAdUnit(constants.VD_PUB_ADUNIT_ID, adsize.getWidth(), adsize.getHeight(), VideoAdUnit.PlacementType.IN_BANNER);
                Log.e("constants.VD_PUB_ADUNIT",constants.VD_PUB_ADUNIT_ID + " ");
            } else {
                this.adUnit = new BannerAdUnit(constants.PUB_ADUNIT_ID, adsize.getWidth(), adsize.getHeight());
                Log.e("constants.VD_PUB_ADUNIT",constants.PUB_ADUNIT_ID + " ");
            }
        } catch (Exception e) {
            if (adsize == null){
                Log.e("Err LoadAd","Size for banner NULL");
            }
        }
    }

    public void setAdUnitID(String adUnitID){
        this.adUnitID = adUnitID;
    }

    public void setPlacement(String placement){
        constants.getConfigOfPlacement(placement,context);
    }

    public Banner(Context context){
        this.context = context;
        checkOnComplete = new CheckOnComplete(30000,3000);
        constants = new Constants();
    }

    private void setUpBanner(final FrameLayout adFrame){
        this.amBanner = new PublisherAdView(context);
        if (typeAd == TypeAd.VIDEO){
            amBanner.setAdUnitId(constants.VD_DFP_ADUNIT_ID_Prebid);
            Log.e("constants.VD_DFP_ADUNIT",constants.VD_DFP_ADUNIT_ID_Prebid + " ");
        } else {
            amBanner.setAdUnitId(constants.DFP_ADUNIT_ID_Prebid);
        }
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
                if (typeAd == TypeAd.VIDEO) {
                    typeAd = TypeAd.BANNER;
                    loadAd(layout);
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
    class CheckOnComplete extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CheckOnComplete(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (constants.isOnCompleteConfig()){
                onFinish();
                cancel();
            }
        }

        @Override
        public void onFinish() {
            if (constants.isOnCompleteConfig()){
                setUpConfigAndLoad();
            } else
                Log.e("Err","Fetch config false!");
        }
    }

}
