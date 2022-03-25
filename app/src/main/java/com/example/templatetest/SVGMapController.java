package com.example.templatetest;


import android.graphics.PointF;
import com.example.templatetest.core.componet.MapMainView;


public class SVGMapController
{
    private MapMainView mapMainView;

    public SVGMapController(SVGMapView mapView)
    {
        this.mapMainView = (MapMainView) mapView.getChildAt(0);
    }

    /**
     * 设置是否开启地图手势移动，默认开启
     *
     * @param enabled true/false
     */
    public void setScrollGestureEnabled(boolean enabled)
    {
        this.mapMainView.setScrollGestureEnabled(enabled);
    }


    public void setZoomGestureEnabled(boolean enabled)
    {
        this.mapMainView.setZoomGestureEnabled(enabled);
    }


    public void setRotationGestureEnabled(boolean enabled)
    {
        if (!enabled)
        {
            setCurrentRotationDegrees(0);
        }
        this.mapMainView.setRotationGestureEnabled(enabled);

    }


    public void setZoomWithTouchEventCenterEnabled(boolean enabled)
    {
        this.mapMainView.setZoomWithTouchEventCenter(enabled);
    }


    public void setRotateWithTouchEventCenterEnabled(boolean enabled)
    {
        this.mapMainView.setRotateWithTouchEventCenter(enabled);
    }


    public void translateBy(float x, float y)
    {
        this.mapMainView.translateBy(x, y);
    }

    public void setCurrentZoomValue(float zoom, float pivotX, float pivotY)
    {
        this.mapMainView.setCurrentZoomValue(zoom, pivotX, pivotY);
    }

    public void setCurrentZoomValue(float zoom)
    {
        setCurrentZoomValue(zoom, mapMainView.getWidth() / 2, mapMainView.getHeight() / 2);
    }

    public void setCurrentRotationDegrees(float degrees)
    {
        setCurrentRotationDegrees(degrees, mapMainView.getWidth() / 2, mapMainView.getHeight() / 2);
    }

    public void setCurrentRotationDegrees(float degrees, float pivotX, float pivotY)
    {
        this.mapMainView.setCurrentRotationDegrees(degrees, pivotX, pivotY);
    }

    public void setMaxZoomValue(float maxZoomValue)
    {
        this.mapMainView.setMaxZoomValue(maxZoomValue);
    }

    public void sparkAtPoint(PointF point, float radius, int color, int repeatTimes)
    {
        this.mapMainView.sparkAtPoint(point, radius, color, repeatTimes);
    }

}
