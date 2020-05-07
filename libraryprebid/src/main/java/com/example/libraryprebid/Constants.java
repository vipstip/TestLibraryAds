/*
 *    Copyright 2018-2019 Prebid.org, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.libraryprebid;


class Constants {
    private Constants() {
    }

    //AppNexus
    // Prebid server config ids
//    private static final String PBS_ACCOUNT_ID_APPNEXUS = "e8df28e7-78ff-452d-b3af-ff4df83df832";
    private static final String PBS_ACCOUNT_ID_APPNEXUS = "bfa84af2-bd16-4d35-96ad-31c6bb888df0";
    // DFP ad unit ids
    private static final String DFP_ADUNIT_ID_Prebid = "/307492156/Prebid_Display";
//    private static final String DFP_ADUNIT_ID_Prebid = "/5300653/test_adunit_vast_pavliuchyk";
    private static final String DFP_IN_BANNER_NATIVE_ADUNIT_ID_APPNEXUS = "/19968336/Wei_Prebid_Native_Test";

    static String PBS_ACCOUNT_ID = PBS_ACCOUNT_ID_APPNEXUS;
    // DFP ad unit ids
    static String DFP_ADUNIT_ID = DFP_ADUNIT_ID_Prebid;
    static String DFP_BANNER_NATIVE_ADUNIT_ID = DFP_IN_BANNER_NATIVE_ADUNIT_ID_APPNEXUS;


}
