package com.example.beatyourbestapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class RoundedImageView extends AppCompatImageView {

    public RoundedImageView(@NonNull Context context) {
        super(context);
    }

    public RoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        adjustViewBounds();
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        adjustViewBounds();
    }

    private void adjustViewBounds() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int width = getWidth();
            int height = getHeight();
            int minSize = Math.min(width, height);
            float radius = minSize / 2.0f;

            // Set rounded corners based on image dimensions
            setCornerRadius(radius);
        }
    }

    private void setCornerRadius(float radius) {
        // Apply rounded corners to the ImageView
        // You can use any method you prefer to apply rounded corners, such as creating a rounded shape drawable
    }
}
