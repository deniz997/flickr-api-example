package com.example.flickrapiexample.FlickrRepository;


import com.example.flickrapiexample.Model.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    //Structured call for get request on flicker, returns FlickerResponse
    @GET("rest/")
    Call<FlickrResponse> getPhotoModels(
            @Query("method") String method,
            @Query("api_key") String api_key,
            @Query("perpage") int perpage,
            @Query("page") int page,
            @Query("format") String format,
            @Query("nojsoncallback") int nocallback);

}
