package com.adhamenaya.grability.appsfeed.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.GET;
import rx.Observable;
import com.adhamenaya.grability.appsfeed.data.model.RssFeed;

public interface RssService {

    String ENDPOINT = "https://itunes.apple.com/us/rss/topfreeapplications/";

    @GET("limit=60/json")
    Observable<RssFeed> getRssFeed();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static RssService newRssService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(RssService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RssService.class);
        }
    }
}
