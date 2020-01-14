package com.example.flickrapiexample.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.flickrapiexample.Constants;

public class NoPhotoDialog extends AppCompatDialogFragment {

    private static final String TAG = "NoPhotoDialog: ";
    private NoPhotoDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Create dialog and init elements
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
         builder.setTitle(Constants.ALERT_TITLE).setMessage(Constants.ALERT_MESSAGE)
         .setPositiveButton(Constants.ALERT_POS, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                listener.onAgainClicked();
             }
         })
         .setNegativeButton(Constants.ALERT_NEG, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                listener.onExitClicked();
             }
         });

    return builder.create();
    }

    //Set listener
    public void setListener(NoPhotoDialogListener noPhotoDialogListener){
        this.listener=noPhotoDialogListener;
    }

    public interface NoPhotoDialogListener{
        void onAgainClicked();
        void onExitClicked();
    }
}
