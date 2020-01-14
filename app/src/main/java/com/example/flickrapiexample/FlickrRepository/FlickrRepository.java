package com.example.flickrapiexample.FlickrRepository;

import android.util.Log;


import com.example.flickrapiexample.FlickrViewModel.FlickrViewModel;
import com.example.flickrapiexample.Model.FlickrResponse;
import com.example.flickrapiexample.Model.Photos;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.flickrapiexample.Constants.*;

public class FlickrRepository {

    private static final String TAG = "FlickrRepository: ";
    private static FlickrRepository instance;
    private FlickrViewModel attachedVM;

    //Singleton Pattern
    public static FlickrRepository getInstance() {
        if (instance == null) {
            instance = new FlickrRepository();
        }
        return instance;
    }

    public void initFlickr(FlickrViewModel viewModel){
        //Remember viewMModel
        attachedVM = viewModel;
    }

    //Retrieve photos urls
    public void retrievePhotoUrls(){
        Log.i(TAG,"Req on repository");
        Call<FlickrResponse> call = getPhotoService().getPhotoModels(METHOD_GET_RECENT,API_KEY, PESPONSE_PERPAGE, RESPONSE_PAGE,RESPONSE_FORMAT,RESPONSE_NOCALLBACK);
        Log.w(TAG,"Request: " + call.request().toString());
        Log.i(TAG,"Dispatching from main thread");

        //Dispatch from main ui thread for network operations
        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                notifyResponseStart();
                if(!response.isSuccessful()){
                    //If HTTP Error occurs
                    Log.e(TAG,"ErrorCode on response: " + response.code());
                    return;
                }
                //Convert JSON to POJO
                FlickrResponse flickrResponse = response.body();
                Log.i(TAG, "Stat: " + flickrResponse.getStat());
                Photos photoList = flickrResponse.getPhotos();
                if(photoList.getPhotos().isEmpty()){
                    Log.w(TAG, "No image returned in response");
                }
                Log.i(TAG,"Response: " + response.toString());
                //Send retrieved photo list to viewModel to update adapter
                if(!photoList.getPhotos().isEmpty()){
                attachedVM.setPhotoList(photoList.getPhotos());
                }else{
                    notifyNoPhotoReturned();
                }
            }
            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                //If connection error occurs
                Log.e(TAG,"Error: " + t.getMessage());
            }
        });
    }

    private static JsonPlaceHolderApi getPhotoService(){
        //Create service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceHolderApi.class);
    }

    //Notify liveData on viewModel if no photo returned in response
    private void notifyNoPhotoReturned(){
        attachedVM.noDataReturned();
    }

    //Notify liveData on viewModel on arrival of new response
    private void notifyResponseStart(){
        attachedVM.responseStart();
    }
}
