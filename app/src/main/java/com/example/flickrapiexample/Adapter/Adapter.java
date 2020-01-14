package com.example.flickrapiexample.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flickrapiexample.Model.Photos;
import com.example.flickrapiexample.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyPhotoHolder> {

    private static final String TAG = "AdapterFeed: ";

    private Context mContext;
    private ArrayList<Photos.Photo> myPhotoList;
    private OnPhotoListener photoListener;


    //Constructor
    public Adapter(Context context, ArrayList<Photos.Photo> photoList, OnPhotoListener listener) {
        this.mContext = context;
        this.myPhotoList = photoList;
        this.photoListener = listener;
    }

    @NonNull
    @Override
    public MyPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.photo_card, parent, false);
        return new MyPhotoHolder(v,photoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyPhotoHolder holder, int position) {
        Photos.Photo tempPhoto = myPhotoList.get(position);
        //Retrieve thumbnail
        //Create thumbnail url
        String imgUrl = "https://farm"
                + tempPhoto.getFarm() + ".staticflickr.com/"
                + tempPhoto.getServer() + "/"
                + tempPhoto.getId() + "_" + tempPhoto.getSecret() + "_t.jpg";

        //Check if title is more than 14 char, add "..." otherwise
        if (tempPhoto.getTitle().length() >= 14) {
            holder.mTitleView.setText(tempPhoto.getTitle().substring(0, 14) + "...");
        } else {
            holder.mTitleView.setText(tempPhoto.getTitle());
        }

        //Retrieve thumbnail
        try {
            Picasso.get().load(imgUrl).error(R.drawable.ic_nophotoplaceholder).placeholder(R.drawable.ic_nophotoplaceholder).into(holder.mImageView, new Callback() {
                @Override
                public void onSuccess() {
                    //Config progressBar
                    if (holder.progressBar != null) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Exception e) {

                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return myPhotoList.size();
    }

    public ArrayList<Photos.Photo> getMyPhotoList() {
        return myPhotoList;
    }

    //set photoList for update purposes
    public void setMyPhotoList(ArrayList<Photos.Photo> myPhotoList) {
        this.myPhotoList = myPhotoList;
    }


    class MyPhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitleView;
        ImageView mImageView;
        ProgressBar progressBar;
        OnPhotoListener listener;

        public MyPhotoHolder(@NonNull View itemView, OnPhotoListener listener) {
            super(itemView);
            //Init photoHolder and add listener
            progressBar = itemView.findViewById(R.id.progress_circular);
            mTitleView = itemView.findViewById(R.id.card_title);
            mImageView = itemView.findViewById(R.id.img_feed);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onPhotoClicked(getAdapterPosition());
        }
    }

    //Check if photo clicked
    public interface OnPhotoListener{
        void onPhotoClicked(int position);
    }
}
