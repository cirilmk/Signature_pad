package com.ciril.scanner_signaturepad.Activities;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.ciril.scanner_signaturepad.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public abstract class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ViewGroup contentFrame;
    protected ZXingScannerView  mScannerView;  // include barcode scanner plugin in gradle file  (eg: implementation 'me.dm7.barcodescanner:zxing:1.9.8')  u can use the latest versions.

    @Override
    public abstract void handleResult(Result result);

    public void initScanner() {
        contentFrame = findViewById(R.id.content_frame);  // Id of FrameLayout in the target Activity (Page), here Id of FrameLayout in sampleActivity
        mScannerView = new ZXingScannerView(this);

        contentFrame.addView(mScannerView);

        mScannerView.setResultHandler(this);
    }

    public void openCamera() {
        if(mScannerView != null)
            mScannerView.startCamera();
    }

}
