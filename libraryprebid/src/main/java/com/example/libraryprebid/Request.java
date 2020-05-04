package com.example.libraryprebid;

import com.google.android.gms.ads.doubleclick.PublisherAdRequest;

class Request {
    private PublisherAdRequest request;
    public Request() {
    }

    public void CreateRequest(){
        final PublisherAdRequest.Builder builder = new PublisherAdRequest.Builder();
        request = builder.build();
    }

    public PublisherAdRequest getRequest() {
        return request;
    }
}
