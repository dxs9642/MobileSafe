package com.dream.net.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sunlongxiao on 10/14/14.
 */
public class FocusTextView extends TextView{

    public FocusTextView(Context context) {
        super(context);
    }

    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FocusTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        //这里欺骗了系统将textview的focus设置成了true，即一直获取焦点来导致文字滚动
        return true;
    }
}
