package com.tot_up.chris.tot_up.util.CustomFabToolbar;

import android.support.v4.view.ViewCompat;
import android.view.View;

//Credit goes to: https://github.com/bowyer-app/fab-toolbar for this code
//for my use case I needed a more customisable layout so am replicating the code here
//with some changes as in original library methods I need to modify were private.

public class ViewUtils {

    public static boolean setVisibility(View v, boolean visible) {
        int visibility;
        if (visible) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.GONE;
        }
        v.setVisibility(visibility);
        return visible;
    }

    public static boolean setInVisibility(View v, boolean visible) {
        int visibility;
        if (visible) {
            visibility = View.VISIBLE;
        } else {
            visibility = View.INVISIBLE;
        }
        v.setVisibility(visibility);
        return visible;
    }

    public static float centerX(View view) {
        return ViewCompat.getX(view) + view.getWidth() / 2f;
    }

    public static float centerY(View view) {
        return ViewCompat.getY(view) + view.getHeight() / 2f;
    }

    public static int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView())
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    private ViewUtils() {
    }
}
