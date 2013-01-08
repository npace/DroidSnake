
package com.npace.snake.framework.impl;

import java.util.List;

import com.npace.snake.framework.Input.AbsTouchEvent;

import android.view.View.OnTouchListener;

public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<AbsTouchEvent> getTouchEvents();
}
