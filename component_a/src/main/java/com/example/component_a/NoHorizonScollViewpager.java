package com.example.component_a;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoHorizonScollViewpager extends ViewPager {

	private int downX;
	private int downY;
	private int moveX;
	private int moveY;
	private boolean canScroll = false;
	public NoHorizonScollViewpager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoHorizonScollViewpager(Context context) {
		super(context);
	}
		
	public boolean isCanScroll() {
		return canScroll;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (canScroll)
			return super.onInterceptTouchEvent(arg0);
		else
			return false;
	}


	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		if (action== MotionEvent.ACTION_DOWN) {
			downX = (int) e.getRawX();
			downY = (int) e.getRawY();			
		}
		else if (action== MotionEvent.ACTION_MOVE) {
			moveX = (int) e.getRawX();
			moveY = (int) e.getRawY();	
			int diffX=downX-moveX;
			int diffY=downY-moveY;			
					
			if (Math.abs(diffX)> Math.abs(diffY)|| Math.abs(diffX)>0) {
				//说明是横向滑�?				
				return true;
			}			
		}		
		return super.onTouchEvent(e);		
	}
	
	/*@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        
        int height = 0;  
        for(int i = 0; i < getChildCount(); i++) {  
            View child = getChildAt(i);  
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));  
            int h = child.getMeasuredHeight();  
            if(h > height) height = h;  
        }  
  
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY); 
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}*/
	
	/*private int measureHeight(int measureSpec, View view) {  
        int result = 0;  
        int specMode = MeasureSpec.getMode(measureSpec);  
        int specSize = MeasureSpec.getSize(measureSpec);  
  
        if (specMode == MeasureSpec.EXACTLY) {  
            result = specSize;  
        } else {  
            // set the height from the base view if available  
            if (view != null) {  
                result = view.getMeasuredHeight();  
            }  
            if (specMode == MeasureSpec.AT_MOST) {  
                result = Math.min(result, specSize);  
            }  
        }  
        return result;  
    }*/  
}
