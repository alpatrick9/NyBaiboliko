package com.patrick.developer.nybaiboliko.tools.animation;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.patrick.developer.nybaiboliko.R;

/**
 * Created by developer on 10/20/16.
 */

public class AnimationManager {

    public static void setAnimation(Context context, View view) {
        Animation scaleUp = AnimationUtils.loadAnimation(context, R.anim.scale_up_fast);
        view.setAnimation(scaleUp);
    }
}
