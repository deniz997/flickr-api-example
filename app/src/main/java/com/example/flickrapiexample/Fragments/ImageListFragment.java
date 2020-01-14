package com.example.flickrapiexample.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickrapiexample.Adapter.Adapter;
import com.example.flickrapiexample.Constants;
import com.example.flickrapiexample.FlickrViewModel.FlickrViewModel;
import com.example.flickrapiexample.Model.Photos;
import com.example.flickrapiexample.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class ImageListFragment extends Fragment implements NoPhotoDialog.NoPhotoDialogListener, Adapter.OnPhotoListener {

    private static final String TAG = "ImageListFragment: ";

    private Adapter photoAdapter;
    private MaterialProgressBar materialProgressBar;
    private FlickrViewModel viewModel;
    private NoPhotoDialog dialog;
    private ArrayList<Photos.Photo> photoList;
    private Dialog popUpDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list,container,false);
        //Set title
        getActivity().setTitle(Constants.LIST_TITLE);

        //Init PopUpDialog
        popUpDialog = new Dialog(view.getContext());
        //Init horizontal main loading progressBar
        materialProgressBar = view.findViewById(R.id.progress_loading);

        //Init viewModel
        viewModel = ViewModelProviders.of(this).get(FlickrViewModel.class);
        viewModel.FlickrViewModel();
        Log.i(TAG, "ViewModel is created");

        //Init photoList and start retrieving
        photoList = new ArrayList<>();
        viewModel.retrieveImgUrls();

        //Configure horizontal progressBar
        if(photoList.isEmpty()){
            materialProgressBar.setVisibility(View.VISIBLE);
        }else{
            materialProgressBar.setVisibility(View.INVISIBLE);
        }

        //Init and configure - recyclerView and Adapter
        RecyclerView recyclerView = view.findViewById(R.id.img_list);
        photoAdapter = new Adapter(getActivity(), photoList,this);

        recyclerView.setAdapter(photoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);

        //Set ViewModel Observers
        //Observe updates on photoList
        viewModel.getPhotoList().observe(this, new Observer<List<Photos.Photo>>() {
            @Override
            public void onChanged(List<Photos.Photo> photoFlickrs) {
                ArrayList<Photos.Photo> photos = (ArrayList<Photos.Photo>) photoFlickrs;
                Log.i(TAG, "Added photoList: \n" + photos.toString());
                updateAdapter(photos);
            }
        });
        //Observe if no data returned
        viewModel.getIsNoData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    openDialog();
                }
            }
        });
        return view;
    }

    //Update adapter on change on photoList
    private void updateAdapter(ArrayList<Photos.Photo> photoList){
        //Update progressBar
        this.photoList = photoList;
        if(materialProgressBar.getVisibility() == View.VISIBLE){
            materialProgressBar.setVisibility(View.INVISIBLE);
        }
        photoAdapter.setMyPhotoList(this.photoList);
        photoAdapter.notifyDataSetChanged();
        Log.i(TAG, "Adapter updated");
    }

    private void openDialog(){
        //Show dialog if no data returned
        dialog = new NoPhotoDialog();
        dialog.setListener(this);
        dialog.show(getFragmentManager(),TAG);
    }

    @Override
    public void onAgainClicked() {
        //Send request again and update horizontal progressBar
        viewModel.retrieveImgUrls();
        materialProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExitClicked() {
        //Exit app by finishing activity
        getActivity().finish();
    }

    @Override
    public void onPhotoClicked(int position) {
        //On photo item clicked
        Log.e(TAG,"Clicked on: " + position);
        showPopUp(position);
    }

    public void showPopUp(int pos){
        //Get index of clicked item
        Photos.Photo popPhoto = photoList.get(pos);
        //Create image url for clicked item
        String imgUrl = "https://farm"
                + popPhoto.getFarm() + ".staticflickr.com/"
                + popPhoto.getServer() + "/"
                + popPhoto.getId() + "_" + popPhoto.getSecret() + ".jpg";

        //Create dialog(popUp)
        ImageView imgPopUp;
        final ProgressBar progressBar;
        popUpDialog.setContentView(R.layout.photo_popup);
        imgPopUp = popUpDialog.findViewById(R.id.original_img);
        progressBar = popUpDialog.findViewById(R.id.progress_original);

        //Get medium version of selected thumbnail image
        try {
            Picasso.get().load(imgUrl).error(R.drawable.ic_nophotoplaceholder).placeholder(R.drawable.ic_nophotoplaceholder).into(imgPopUp, new Callback() {
                @Override
                public void onSuccess() {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
        //Show dialog
        popUpDialog.show();
    }
}
