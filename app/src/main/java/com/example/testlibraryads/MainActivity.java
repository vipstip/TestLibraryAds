package com.example.testlibraryads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.libraryprebid.AdListeners;
import com.example.libraryprebid.AdManager;

import com.example.libraryprebid.BannerSize;
import com.example.libraryprebid.BannerTest;
import com.example.libraryprebid.InterstitialVideo;
import com.example.libraryprebid.RewardedAdVideoCallback;
import com.example.libraryprebid.RewardedAdVideoLoadCallback;
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
    AdUnit adUnit;
    RewardedAd amRewardedAd;
    PublisherAdRequest request;
    ResultCode resultCode;
    InterstitialVideo interstitialVideo;
//    BannerTest adManager;

    int dem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btnad);
        adView = findViewById(R.id.adView);

        adManager = new AdManager(MainActivity.this);
        adManager.setTypeAd(TypeAd.BANNER);
        adManager.setSize(BannerSize.LARGE_BANNER);
//        adManager.setID("1001-1");

        adManager.setID("28226736-c0be-4cd6-961f-fd1ef48b058b");
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
                adManager.loadAd(adView);
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
    private void setupAndLoadAMRewardedVideo() {
        setupPBRewardedVideo();
        setupAMRewardedVideo();
        loadAMRewardedVideo();
    }

    private void setupPBRewardedVideo() {

//        PrebidMobile.setPrebidServerHost(Host.RUBICON);
//        PrebidMobile.setPrebidServerAccountId("1001");
//        PrebidMobile.setStoredAuctionResponse("sample_video_response");

        PrebidMobile.setPrebidServerHost(Host.APPNEXUS);
        PrebidMobile.setPrebidServerAccountId("e8df28e7-78ff-452d-b3af-ff4df83df832");

        adUnit = new RewardedVideoAdUnit("ac84fd30-aee6-46fe-adcf-b0327181b537");

    }

    private void setupAMRewardedVideo() {

        amRewardedAd = new RewardedAd(this, "/307492156/Prebid_Display");
    }

    private void loadAMRewardedVideo() {

        PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
        adUnit.fetchDemand(request, new OnCompleteListener() {
            @Override
            public void onComplete(ResultCode resultCode) {
                MainActivity.this.resultCode = resultCode;
                amRewardedAd.loadAd(request, new RewardedAdLoadCallback() {
                    @Override
                    public void onRewardedAdLoaded() {
                        // Ad successfully loaded.

                        if (amRewardedAd.isLoaded()) {
                            amRewardedAd.show(MainActivity.this, new RewardedAdCallback() {
                                @Override
                                public void onRewardedAdOpened() {
                                    // Ad opened.
                                }

                                @Override
                                public void onRewardedAdClosed() {
                                    // Ad closed.
                                }

                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem reward) {
                                    Log.e("Reward2:","ok");
                                }

                                @Override
                                public void onRewardedAdFailedToShow(int errorCode) {
                                    // Ad failed to display.
                                    Log.e("Err",errorCode+"");
                                }
                            });
                        }
                    }

                    @Override
                    public void onRewardedAdFailedToLoad(int errorCode) {
                        // Ad failed to load.
                    }
                });
            }
        });
    }
}
