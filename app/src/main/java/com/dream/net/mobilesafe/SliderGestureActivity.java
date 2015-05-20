package com.dream.net.mobilesafe;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import com.dream.net.ui.anim.ShowByBehavior;

/**
 * Created by sunlongxiao on 5/20/15.
 */
public abstract class SliderGestureActivity  extends Activity {
    private GestureDetector gusture;


    protected abstract void showPre();
    protected abstract void showNext();

    /**
     * 下面是滑动进入下个界面
     *
     */

    protected void setupGesture(){

        gusture = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (e2.getRawX() - e1.getRawX() > 200){
                    showPre();
                    return true;
                }

                if (e1.getRawX() - e2.getRawX() > 200){
                    showNext();
                    return true;
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gusture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

}
