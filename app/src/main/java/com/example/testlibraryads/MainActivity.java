package com.example.testlibraryads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.libraryprebid.AdManager;

import com.example.libraryprebid.AdSizes;
import com.example.libraryprebid.Banner;
import com.example.libraryprebid.Interstitial;
import com.example.libraryprebid.NativeAd;
import com.example.libraryprebid.TypeAd;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.rewarded.RewardedAd;

import org.prebid.mobile.AdUnit;
import org.prebid.mobile.ResultCode;


public class MainActivity extends AppCompatActivity {
    Button button;
    FrameLayout adView,adView2;
    AdManager adManager;
    Banner banner;
    Interstitial interstitial;
    AdUnit adUnit;
    RewardedAd amRewardedAd;
    PublisherAdRequest request;
    ResultCode resultCode;
    NativeAd nativeAd;
    int dem;
    private NativeAd nativeAd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adView = (FrameLayout) findViewById(R.id.adView);
        adView2 = (FrameLayout) findViewById(R.id.adView2);
        button = findViewById(R.id.btnad);
//        adView = findViewById(R.id.adView);
//
//        banner = new Banner(MainActivity.this);
//        banner.setTypeAd(TypeAd.VIDEO);
//        banner.setSize(AdSizes.MEDIUM_RECTANGLE);
//        banner.setAdUnitID("tag_video");
//        banner.setAdUnitID("1001-1");
//        adManager.setID("1001-1");
//        banner.setAdUnitID("28226736-c0be-4cd6-961f-fd1ef48b058b");
//        banner.setAdUnitID("6ace8c7d-88c0-4623-8117-75bc3f0a2e45");
//        banner.setTypeAd(TypeAd.BANNER);
//        interstitial.setAdUnit("625c6125-f19e-4d5b-95c5-55501526b2a4");
        interstitial = new Interstitial(this);
        interstitial.setTypeAd(TypeAd.VIDEO);
            interstitial.setAdUnit("1001-1");
//            interstitial.setAdUnit("tag_video");
//        adManager.setID("ac84fd30-aee6-46fe-adcf-b0327181b537");
//        adManager.setID("965fbcb7-520b-4fae-a88c-4f0fe553adf7");
//        adManager.setMillisAutoRefres(40000);

//        interstitialVideo = new InterstitialVideo(MainActivity.this);
//        interstitialVideo.setAdUnit("1001-1");

//        nativeAd = new NativeAd(this);
//        nativeAd.setAdUnit("25e17008-5081-4676-94d5-923ced4359d3");
//
//        nativeAd2 = new NativeAd(this);
//        nativeAd.setAdUnit("25e17008-5081-4676-94d5-923ced4359d3");
//        banner.setMillisAutoRefres(30000);
//        dem = 0;
//        interstitial.loadAd();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setupAndLoadAMRewardedVideo();
//                banner.stopAutoRefresh();
//                banner.loadAd(adView);
                    interstitial.loadAd();
//                adUnit = new NativeAdUnit("25e17008-5081-4676-94d5-923ced4359d3");
//                createDFPNative();
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
