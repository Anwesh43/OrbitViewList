package com.anwesome.ui.orbitviewlist;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by anweshmishra on 09/05/17.
 */
public class OrbitList {
    private Activity activity;
    private OrbitGridLayout orbitGridLayout;
    private boolean isShown = false;
    private ScrollView scrollView;
    public OrbitList(Activity activity) {
        this.activity = activity;
        this.orbitGridLayout = new OrbitGridLayout(activity);
        this.scrollView = new ScrollView(activity);
        scrollView.addView(orbitGridLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }
    public void addOrbit() {
        if(!isShown) {
            orbitGridLayout.addOrbit();
        }
    }
    public void show() {
        if(!isShown) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activity.setContentView(scrollView);
            isShown = true;
        }
    }
}
