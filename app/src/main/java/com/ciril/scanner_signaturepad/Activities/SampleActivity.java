package com.ciril.scanner_signaturepad.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ciril.scanner_signaturepad.Classes.CaptureSignatureView;
import com.ciril.scanner_signaturepad.R;
import com.ciril.scanner_signaturepad.Utils.DisplayInfo;
import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;

public class SampleActivity extends ScannerActivity  implements View.OnClickListener{

    Button btn_clear, btn_save;
    Button btn_retake, btn_close;
    TextView txt_scan_text;
    LinearLayout mSignLayout;
    ImageView iv_camera;

    CaptureSignatureView mSig;

    String[] PERMISSIONS_SET = {
            Manifest.permission.CAMERA
    };

    boolean cameraPermission = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        checkPermission();

    }

    @Override
    public void handleResult(Result result) {

        txt_scan_text.setText(result.getText());
        DisplayInfo.showShortToast(this, R.string.msg_detect_close);

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mScannerView != null)
            mScannerView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mScannerView != null)
            mScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        if(!cameraPermission){
            super.onBackPressed();
        }else{
            if((int) iv_camera.getTag() != 0 ){
                setScanText();
            }else{
                super.onBackPressed();
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                // Your code here   You will get the signature bitmap / byte / base64 format using the methods -> mSig.getBitmap(); , mSig.getBytes(); , mSig.getBase64();
                DisplayInfo.showShortToast(this, R.string.msg_info);
                break;
            case R.id.btn_clear:
                mSig.ClearCanvas();
                txt_scan_text.setText("");
                break;
            case R.id.btn_retake:
                clearPreview();
                break;
            case R.id.btn_close:
                setScanText();
                break;
            case R.id.iv_add_scan_data:
                setScanText();
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            initUI();
                        }
                        else{
                            cameraPermission = false;
                            DisplayInfo.showShortToast(this, R.string.msg_permission);
                        }

                    }
                }
            }


        }
    }




    private void checkPermission(){
        if ((int) Build.VERSION.SDK_INT >= 23) {
            int hasPermission = ContextCompat.checkSelfPermission(SampleActivity.this, Manifest.permission.CAMERA);

            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_SET, 1);
            }else{
                initUI();
            }
        }
    }

    private void initUI() {
        btn_save = findViewById(R.id.btn_save);
        btn_clear = findViewById(R.id.btn_clear);
        btn_retake = findViewById(R.id.btn_retake);
        btn_close = findViewById(R.id.btn_close);
        txt_scan_text = findViewById(R.id.txt_scan_text);
        mSignLayout = findViewById(R.id.signLayout);
        iv_camera = findViewById(R.id.iv_add_scan_data);
        iv_camera.setTag(0);

        mSig = new CaptureSignatureView(this, null);
        mSignLayout.addView(mSig, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);


        initScanner();
        setClickListeners();
    }

    private void setClickListeners() {
        btn_clear.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_retake.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        iv_camera.setOnClickListener(this);

    }

    private void setScanText(){
        int tag = (int) iv_camera.getTag();
        if (tag == 0) {
            findViewById(R.id.content_frame_layout).setVisibility(View.VISIBLE);
            iv_camera.setTag(1);
            if(mScannerView != null) {
                mScannerView.startCamera();
                mScannerView.resumeCameraPreview(SampleActivity.this);
            }
        } else {
            findViewById(R.id.content_frame_layout).setVisibility(View.GONE);
            iv_camera.setTag(0);
            if(mScannerView != null)
                mScannerView.stopCamera();
        }
    }

    private void clearPreview(){
        txt_scan_text.setText("");
        mScannerView.resumeCameraPreview(SampleActivity.this);
    }

}
