package com.example.templatetest;


import android.graphics.Bitmap;


public interface SVGMapViewListener
{
    void onMapLoadComplete();

    void onMapLoadError();

    void onGetCurrentMap(Bitmap bitmap);
}
