package com.example.libraryprebid;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.RewardedVideoAdUnit;

public class RewardedAdVideo extends SetupPB {

    private int refreshCount = 0;
    private AdUnit adUnit;
    private ResultCode resultCode;
    private PublisherAdRequest request;
    private Context context;
    private RewardedAdVideoLoadCallback rewardedAdVideoLoadCallback;
    private RewardedAdVideoCallback rewardedAdVideoCallback;
    private AdListeners adListeners;
    private RewardedAd amRewardedAd;
    private Activity activity;

    void loadAd(Activity activity) {
        SetupPBCustum();
        setupAMRewardedVideo();
        loadAMRewardedVideo(activity);
    }

    public int getRefreshCount() {
        return refreshCount;
    }

    void setAdUnit(String adUnitID){
        this.adUnit = new RewardedVideoAdUnit("1001-1");
    }

    private void setupAMRewardedVideo() {
        amRewardedAd = new RewardedAd(context, Constants.DFP_ADUNIT_ID);
    }

    RewardedAdVideo(Context context){
        super();
        this.context = context;
    };

    private void loadAMRewardedVideo(final Activity activity) {
        this.activity = activity;
        PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                RewardedAdVideo.this.resultCode = resultCode;
                amRewardedAd.loadAd(request, new RewardedAdLoadCallback() {
                    @Override
                    public void onRewardedAdLoaded() {
                        // Ad successfully loaded.
                        if (amRewardedAd.isLoaded()) {
                            amRewardedAd.show(activity, new RewardedAdCallback() {
                                @Override
                                public void onRewardedAdOpened() {
                                    // Ad opened.
                                    rewardedAdVideoCallback.onRewardedAdOpened();
                                }

                                @Override
                                public void onRewardedAdClosed() {
                                    // Ad closed.
                                    rewardedAdVideoCallback.onRewardedAdClosed();
                                }

                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                                    rewardedAdVideoCallback.onUserEarnedReward(rewardItem);
                                }

                                @Override
                                public void onRewardedAdFailedToShow(int errorCode) {
                                    // Ad failed to display.
                                    rewardedAdVideoCallback.onRewardedAdFailedToShow(errorCode);
                                }
                            });
                        }
                        rewardedAdVideoLoadCallback.onRewardedAdVideoLoaded();
                    }

                    @Override
                    public void onRewardedAdFailedToLoad(int errorCode) {
                        // Ad failed to load.
                        rewardedAdVideoLoadCallback.onRewardedAdVideoFailedToLoad(errorCode);
                    }
                });
            }
        });
    }
    void setRewardedAdVideoLoadCallback(final RewardedAdVideoLoadCallback rewardedAdVideoLoadCallback){
        this.rewardedAdVideoLoadCallback = rewardedAdVideoLoadCallback;
    }

    void setRewardedAdVideoCallback(RewardedAdVideoCallback rewardedAdVideoCallback){
        this.rewardedAdVideoCallback = rewardedAdVideoCallback;
    }

    void stopAutoRefresh() {
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
        }
    }

    void startAutoRefresh(){
        loadAMRewardedVideo(activity);
    }

    void desTroy(){
        if (adUnit != null) {
            adUnit.stopAutoRefresh();
            adUnit = null;
        }
    }
}
