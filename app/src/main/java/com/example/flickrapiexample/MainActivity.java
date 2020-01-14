package com.example.flickrapiexample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;

import static com.example.flickrapiexample.Constants.INTERNET_PERMISSOIN_REQ;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity: ";

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check internet permission
        checkPermission();
        //Check internet connection
        if(!checkConnection()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(Constants.ALERT_NOCONTITLE);
            builder.setMessage(Constants.ALERT_NOCONMESSAGE);
            builder.setNegativeButton(Constants.ALERT_NOCONBTN, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.create();
            builder.show();
        }

        //NavController automatically inflates ImageListFragment
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

    }

    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }else{
            return false;
        }
    }
    public void checkPermission(){
        //Check internet permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            //Ask for permission if not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
            } else {

                //Request permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.INTERNET},
                        INTERNET_PERMISSOIN_REQ);
            }
        } else {
            // Permission has already been granted
            Log.i(TAG, "Internet Permission granted");
        }
    }

    //Get permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case INTERNET_PERMISSOIN_REQ: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted
                } else {
                    //Permission denied
                }
                return;
            }
        }
    }

}
