package com.example.panda.ufanetservicecenter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;


public class FragmentCamera extends Fragment implements View.OnClickListener, ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    MediaPlayer note;
    private static final String TAG = "FragmentCamera";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_camera, container, false);

        scannerView = new ZXingScannerView(getActivity());
        getActivity().setContentView(scannerView);
        note = MediaPlayer.create(getActivity(), R.raw.note);
        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
            } else {
                requestPermission();
            }
        }
        return fragView;
    }

    @Override
    public void onClick(View view) {

    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED);
    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
    }


    @Override
    public void onResume() {
        super.onResume();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (scannerView == null) {
                    scannerView = new ZXingScannerView(getActivity());
                    getActivity().setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {

                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("Вы должны разрешить доступ",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("ОК", okListener)
                .setNegativeButton("Отмена", null)
                .create()
                .show();
    }


    @Override
    public void handleResult(final Result result) {

        long mills = 300L;
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(mills);
        note.start();

        final String myResult = result.getText();
        Log.d("QRCodeScanner", result.getText());
        Log.d("QRCodeScanner", result.getBarcodeFormat().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Результат сканирования");
        builder.setPositiveButton("Учет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), ActivityRegisterMechanism.class);
                intent.putExtra("QRcode", myResult);
                intent.putExtra("booleanCheckDB", true);
                startActivity(intent);
            }
        });

        builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(FragmentCamera.this);
            }
        });

        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }
}