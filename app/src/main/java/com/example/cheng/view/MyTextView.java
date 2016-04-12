package com.example.cheng.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by cheng on 16/4/12.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        if (focused)
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    @Override
    public void onWindowFocusChanged(boolean focused) {
        if (focused)
            super.onWindowFocusChanged(focused);
    }

    @Override
    public boolean isFocused() {
        return true;//一直返回true，假装这个控件一直获取着焦点
    }
}
