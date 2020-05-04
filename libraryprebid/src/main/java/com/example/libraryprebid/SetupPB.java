package com.example.libraryprebid;

import org.prebid.mobile.Host;
import org.prebid.mobile.PrebidMobile;

class SetupPB {
    void SetupPBCustum(){
//        Host.CUSTOM.setHostUrl("https://s2s.valueimpression.com/openrtb2/auction");
//        Host.CUSTOM.setHostUrl("https://pb-server.vliplatform.com/openrtb2/auction");
//        PrebidMobile.setPrebidServerHost(Host.CUSTOM);
//        PrebidMobile.setPrebidServerAccountId("valueimpression_global");

        PrebidMobile.setPrebidServerHost(Host.APPNEXUS);
        PrebidMobile.setPrebidServerAccountId(Constants.PBS_ACCOUNT_ID);
//        PrebidMobile.setPrebidServerAccountId("123456789");
//        PrebidMobile.setPrebidServerHost(Host.RUBICON);
//        PrebidMobile.setPrebidServerAccountId("1001");
//        PrebidMobile.setStoredAuctionResponse("sample_video_response");
    }

    void SetupPBAppnexus(){
        PrebidMobile.setPrebidServerHost(Host.APPNEXUS);
        PrebidMobile.setPrebidServerAccountId(Constants.PBS_ACCOUNT_ID);
    }
}
