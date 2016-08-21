package com.just_app.diplom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.nostra13.universalimageloader.core.imageaware.ViewAware;

public class CustomViewAware extends ViewAware {
    Context context;

    public CustomViewAware(View view, Context ctx) {
        super(view);
        this.context= ctx;
    }

    @Override
    protected void setImageDrawableInto(Drawable drawable, View view) {
        view.setBackgroundDrawable(drawable);
    }

    @Override
    protected void setImageBitmapInto(Bitmap bitmap, View view) {
       // bitmap.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }
}