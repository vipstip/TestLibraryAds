package com.example.libraryprebid;

import android.content.Context;

import org.prebid.mobile.Host;
import org.prebid.mobile.PrebidMobile;

class SetupPB {
        private Context context;
        private String placement;
        private Constants constants;
        private static final SetupPB instance = new SetupPB();

        //private constructor to avoid client applications to use constructor
        private SetupPB(){
            constants = new Constants();
//            Host.CUSTOM.setHostUrl("https://static.vliplatform.com/test/rp3.php");
            Host.CUSTOM.setHostUrl("https://pb-server.vliplatform.com/openrtb2/auction");
//            PrebidMobile.setPrebidServerHost(Host.CUSTOM);
//            PrebidMobile.setPrebidServerAccountId("vli_video");
//            PrebidMobile.setPrebidServerHost(Host.APPNEXUS);
//            PrebidMobile.setPrebidServerAccountId(Constants.PBS_ACCOUNT_ID);
            PrebidMobile.setPrebidServerHost(Host.RUBICON);
            PrebidMobile.setPrebidServerAccountId("1001");
            PrebidMobile.setStoredAuctionResponse("sample_video_response");
        }

        static SetupPB getInstance(){
            return instance;
        }

        void setPlacement(String placement,Context context){
            this.placement = placement;
        }
}
