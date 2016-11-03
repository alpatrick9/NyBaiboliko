package com.patrick.developer.nybaiboliko.tools.animation;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TabHost;

/**
 * Created by patmi on 15/10/2016.
 */
public class TabAnimation {
    protected static float lastX;

    public static Animation inFromRightAnimation()
    {
        Animation inFromRight = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        inFromRight.setDuration(240);
        inFromRight.setInterpolator(new AccelerateInterpolator());
        return inFromRight;
    }

    public static Animation outToRightAnimation()
    {
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(240);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;
    }

    public static void swipeTabHost(View view, final TabHost tabHost) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    // when user first touches the screen to swap
                    case MotionEvent.ACTION_DOWN:
                        lastX = motionEvent.getX();
                        return true;
                    case MotionEvent.ACTION_UP:
                        float currentX = motionEvent.getX();

                        // if left to right swipe on screen
                        if (lastX < currentX) {

                            switchTabs(false,tabHost);
                        }

                        // if right to left swipe on screen
                        if (lastX > currentX) {
                            switchTabs(true,tabHost);
                        }
                        break;
                }
                return false;
            }
        });
    }

    public static void switchTabs(boolean direction, TabHost tabHost) {
        if (direction) // true = move left
        {
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
            else
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } else
        // move right
        {
            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
                    .getTabCount() - 1))
                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
            else
                tabHost.setCurrentTab(0);
        }
    }
}
