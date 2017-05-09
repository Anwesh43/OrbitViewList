package com.anwesome.ui.orbitviewlist;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.*;

/**
 * Created by anweshmishra on 09/05/17.
 */
public class OrbitGridLayout extends ViewGroup {
    private int w,h;
    public OrbitGridLayout(Context context) {
        super(context);
    }
    public void initDimension(Context context) {
        DisplayManager displayManager = (DisplayManager)context.getSystemService(Context.DISPLAY_SERVICE);
        Display display = displayManager.getDisplay(0);
        if(display != null) {
            Point size = new Point();
            display.getRealSize(size);
            w = size.x;
            h = size.y;
        }
    }
    public void addOrbit() {
        OrbitView orbitView = new OrbitView(getContext());
        addView(orbitView,new LayoutParams(2*w/5,w*w/5));
        requestLayout();
    }
    public void onMeasure(int wspec,int hspec) {
        int newH = 0;
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            measureChild(child,wspec,hspec);
            if(i%2 == 1) {
                newH += w/2+w/20;
            }
        }
        if(getChildCount()%2 != 0) {
            newH+=w/2;
        }
        setMeasuredDimension(w,Math.max(newH+w/20,h));
    }
    public void onLayout(boolean reloaded,int a,int b,int w,int h) {
        int x = w/10,y = w/10;
        for(int i=0;i<getChildCount();i++) {
            View child = getChildAt(i);
            child.layout(x,y,x+child.getMeasuredWidth(),y+child.getMeasuredWidth());
            if(i%2 == 1) {
                x = w/10;
                y += w/2+w/20;
            }
            else {
                x += w/2+w/20;
            }
        }
    }
}
