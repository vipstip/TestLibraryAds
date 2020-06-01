package com.example.libraryprebid;

import android.content.Context;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.LogUtil;
import org.prebid.mobile.NativeAdUnit;
import org.prebid.mobile.NativeDataAsset;
import org.prebid.mobile.NativeEventTracker;
import org.prebid.mobile.NativeImageAsset;
import org.prebid.mobile.NativeTitleAsset;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;

import java.util.ArrayList;

public class NativeAd {

    private AdUnit adUnit;
    private ResultCode resultCode;

    private PublisherAdView nativeAdView;
    private PublisherAdRequest request;

    private int autoRefresh = 30000;
    private int refreshCount = 0;

    private AdListeners adListeners;

    public NativeAd(Context context){
        nativeAdView = new PublisherAdView(context);
    }

    public void setAdUnit(String adUnitID){
        this.adUnit = new NativeAdUnit(adUnitID);
    }

    public void setMillisAutoRefresh(int Millis){
        this.autoRefresh = Millis;
    }

    public void loadAd(FrameLayout adFrame){
        setUp(adFrame);
    }
    private void setUp(FrameLayout adFrame){
        try {
            adFrame.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nativeAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adListeners.onAdLoaded();
                LogUtil.d("ad loaded");
            }
        });
        nativeAdView.setAdSizes(AdSize.FLUID);
//        nativeAdView.setAdUnitId(Constants.DFP_BANNER_NATIVE_ADUNIT_ID);
        adFrame.addView(nativeAdView);
        final PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();

        try {
            NativeAdUnit nativeAdUnit = (NativeAdUnit) adUnit;
            nativeAdUnit.setContextType(NativeAdUnit.CONTEXT_TYPE.SOCIAL_CENTRIC);
            nativeAdUnit.setPlacementType(NativeAdUnit.PLACEMENTTYPE.CONTENT_FEED);
            nativeAdUnit.setContextSubType(NativeAdUnit.CONTEXTSUBTYPE.GENERAL_SOCIAL);
            ArrayList<NativeEventTracker.EVENT_TRACKING_METHOD> methods = new ArrayList<>();
            methods.add(NativeEventTracker.EVENT_TRACKING_METHOD.IMAGE);

            try {
                NativeEventTracker tracker = new NativeEventTracker(NativeEventTracker.EVENT_TYPE.IMPRESSION, methods);
                nativeAdUnit.addEventTracker(tracker);
            } catch (Exception e) {
                e.printStackTrace();

            }
            NativeTitleAsset title = new NativeTitleAsset();
            title.setLength(90);
            title.setRequired(true);
            nativeAdUnit.addAsset(title);
            NativeImageAsset icon = new NativeImageAsset();
            icon.setImageType(NativeImageAsset.IMAGE_TYPE.ICON);
            icon.setWMin(20);
            icon.setHMin(20);
            icon.setRequired(true);
            nativeAdUnit.addAsset(icon);
            NativeImageAsset image = new NativeImageAsset();
            image.setImageType(NativeImageAsset.IMAGE_TYPE.MAIN);
            image.setHMin(200);
            image.setWMin(200);
            image.setRequired(true);
            nativeAdUnit.addAsset(image);
            NativeDataAsset data = new NativeDataAsset();
            data.setLen(90);
            data.setDataType(NativeDataAsset.DATA_TYPE.SPONSORED);
            data.setRequired(true);
            nativeAdUnit.addAsset(data);
            NativeDataAsset body = new NativeDataAsset();
            body.setRequired(true);
            body.setDataType(NativeDataAsset.DATA_TYPE.DESC);
            nativeAdUnit.addAsset(body);
            NativeDataAsset cta = new NativeDataAsset();
            cta.setRequired(true);
            cta.setDataType(NativeDataAsset.DATA_TYPE.CTATEXT);
            nativeAdUnit.addAsset(cta);
            nativeAdUnit.setAutoRefreshPeriodMillis(autoRefresh);
            nativeAdUnit.fetchDemand(request, new OnCompleteListener() {
                @Override
                public void onComplete(ResultCode resultCode) {
                    NativeAd.this.resultCode = resultCode;
                    nativeAdView.loadAd(request);
                    NativeAd.this.request = request;
                    refreshCount++;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setAdListeners(AdListeners adListeners){
        this.adListeners = adListeners;
    }
}
