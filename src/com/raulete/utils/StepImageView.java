package com.raulete.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class StepImageView extends ImageView {

	public boolean isMeasured = true; 
	
	public StepImageView(Context context) {
		super(context);
	}

	public StepImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StepImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try
        {
            Drawable drawable = getDrawable();

            if (drawable == null)
            {
                setMeasuredDimension(0, 0);
            }
            else
            {
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int height = width * drawable.getIntrinsicHeight() / drawable.getIntrinsicWidth();
                setMeasuredDimension(width, height);
            }
        }
        catch (Exception e)
        {
            isMeasured = false;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

}

