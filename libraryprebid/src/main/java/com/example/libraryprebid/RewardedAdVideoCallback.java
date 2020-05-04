package com.example.libraryprebid;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.rewarded.RewardItem;

public abstract class RewardedAdVideoCallback {
    public RewardedAdVideoCallback() {
    }

    public void onRewardedAdOpened() {
    }

    public void onRewardedAdClosed() {
    }

    public abstract void onUserEarnedReward(@NonNull RewardItem reward);

    public void onRewardedAdFailedToShow(int i) {
    }
}
