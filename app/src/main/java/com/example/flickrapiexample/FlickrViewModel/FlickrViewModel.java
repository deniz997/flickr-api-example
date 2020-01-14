package com.example.flickrapiexample.FlickrViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flickrapiexample.FlickrRepository.FlickrRepository;
import com.example.flickrapiexample.Model.Photos;

import java.util.ArrayList;

public class FlickrViewModel extends ViewModel {

    private static final String TAG = "FlickrViewModel: ";

    //Observable data storage
    private MutableLiveData<ArrayList<Photos.Photo>> photoList;
    private MutableLiveData<Boolean> isNoData;

    private FlickrRepository flickrRepo;

    //Init viewModel
    public void FlickrViewModel() {
        flickrRepo = FlickrRepository.getInstance();
        flickrRepo.initFlickr(this);
    }

    //To update photoList from a Async Operation
    public void setPhotoList(ArrayList<Photos.Photo> photoList){
        this.photoList.setValue(photoList);
    }

    //Getters
    public LiveData<ArrayList<Photos.Photo>> getPhotoList() {
        if(photoList == null){
            photoList = new MutableLiveData<>();
        }
        return photoList;
    }

    public LiveData<Boolean> getIsNoData() {
        if(isNoData==null){
            isNoData = new MutableLiveData<>(false);
        }
        return isNoData;
    }

    //Trigger retrieving image urls
    public void retrieveImgUrls(){
        Log.i(TAG, "PhotoUrlReq sent");
        flickrRepo.retrievePhotoUrls();
    }

    //If no photo returned in response && response.isSuccessful
    public void noDataReturned(){
        isNoData.setValue(true);
    }

    //New response arrived, response might have photos
    public void responseStart(){
        isNoData.setValue(false);
    }

}
