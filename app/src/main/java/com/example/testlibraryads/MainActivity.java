package com.example.testlibraryads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.libraryprebid.AdManager;

import com.example.libraryprebid.Banner;
import com.example.libraryprebid.BannerSize;
import com.example.libraryprebid.Interstitial;
import com.example.libraryprebid.TypeAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.Host;
import org.prebid.mobile.OnCompleteListener;
import org.prebid.mobile.PrebidMobile;
import org.prebid.mobile.ResultCode;
import org.prebid.mobile.RewardedVideoAdUnit;


public class MainActivity extends AppCompatActivity {
    Button button;
    FrameLayout adView;
    AdManager adManager;
    Banner banner;
    Interstitial interstitial;
    AdUnit adUnit;
    RewardedAd amRewardedAd;
    PublisherAdRequest request;
    ResultCode resultCode;

    int dem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnad);
        adView = findViewById(R.id.adView);

        interstitial = new Interstitial(MainActivity.this);
        interstitial.setTypeAd(TypeAd.VIDEO);
//        adManager.setID("1001-1");

//        banner.setAdUnit("28226736-c0be-4cd6-961f-fd1ef48b058b");
//        interstitial.setAdUnit("625c6125-f19e-4d5b-95c5-55501526b2a4");
        interstitial.setAdUnit("1001-1");
//        adManager.setID("ac84fd30-aee6-46fe-adcf-b0327181b537");
//        adManager.setID("965fbcb7-520b-4fae-a88c-4f0fe553adf7");
//        adManager.setMillisAutoRefres(40000);

//        interstitialVideo = new InterstitialVideo(MainActivity.this);
//        interstitialVideo.setAdUnit("1001-1");
//        interstitialVideo.setMillisAutoRefres(40000);
//        dem = 0;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setupAndLoadAMRewardedVideo();
                interstitial.loadAd();
//                interstitialVideo.loadAd();
//                adManager.setRewardedAdListenners(new RewardedAdVideoLoadCallback(){
//                    @Override
//                    public void onRewardedAdVideoLoaded() {
//                        super.onRewardedAdVideoLoaded();
//                        Log.e("VideoLoaded:","Ok");
//                    }
//
//                    @Override
//                    public void onRewardedAdVideoFailedToLoad(int i) {
//                        super.onRewardedAdVideoFailedToLoad(i);
//                        Log.e("Failed:",i+"");
//                    }
//                });
//                adManager.setAdListeners(new AdListeners(){
//                    @Override
//                    public void onAdOpened() {
//                        super.onAdOpened();
//                    }
//
//                    @Override
//                    public void onAdLoaded() {
//                        super.onAdLoaded();
//                        Log.e("Loaded","Ok");
//                    }
//
//                    @Override
//                    public void onAdClosed() {
//                        super.onAdClosed();
//                        if(dem < 1) {
//
//                            dem++;
//                        }
//                    }
//                });
            }
        });
    }
}
