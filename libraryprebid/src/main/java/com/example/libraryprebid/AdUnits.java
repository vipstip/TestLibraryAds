package com.example.libraryprebid;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.BannerAdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.VideoAdUnit;
import org.prebid.mobile.addendum.AdViewUtils;
import org.prebid.mobile.addendum.PbFindSizeError;

class AdUnits {
    private ResultCode resultCode;
    private AdUnit adUnit;
    private TypeAd typeAd;
    void setAdUnit(String adUnitID,int with, int height){

        if (typeAd == TypeAd.BANNERVIDEO){
            this.adUnit = new VideoAdUnit(adUnitID, with, height, VideoAdUnit.PlacementType.IN_BANNER);
        }

        else if(typeAd == TypeAd.BANNER)
            this.adUnit = new BannerAdUnit(adUnitID,with,height);
    }

    void setTypeAd(TypeAd typeAd){
        this.typeAd = typeAd;
    }

    void setAutoRefreshPeriodMillis(int Millis){
        adUnit.setAutoRefreshPeriodMillis(Millis);
    }

    void setfindPrebidCreativeSize(final AdViewBanner amBanner){
        AdViewUtils.findPrebidCreativeSize(amBanner.getPBAdview(), new AdViewUtils.PbFindSizeListener() {
            @Override
            public void success(int width, int height) {
                amBanner.setAdSizes(width,height);
            }

            @Override
            public void failure(@NonNull PbFindSizeError error) {
                Log.d("MyTag", "error: " + error);
            }
        });
    }

    void fetchDemand(final PublisherAdRequest request, final AdViewBanner adViewBanner){
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                AdUnits.this.resultCode = resultCode;
                adViewBanner.loadAd(request);
            }
        });
    }

    void stopAutoRefresh(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
        }
    }

    void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }
}
