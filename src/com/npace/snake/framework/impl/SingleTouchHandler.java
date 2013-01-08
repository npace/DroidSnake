
package com.npace.snake.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.npace.snake.framework.Input.AbsTouchEvent;
import com.npace.snake.framework.Pool.PoolObjectFactory;
import com.npace.snake.framework.Pool;

public class SingleTouchHandler implements TouchHandler {
    boolean isTouched;
    int touchX, touchY;
    Pool<AbsTouchEvent> touchEventPool;
    List<AbsTouchEvent> touchEvents = new ArrayList<AbsTouchEvent>();
    List<AbsTouchEvent> touchEventsBuffer = new ArrayList<AbsTouchEvent>();
    float scaleX, scaleY;

    public SingleTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<AbsTouchEvent> factory = new PoolObjectFactory<AbsTouchEvent>() {

            @Override
            public AbsTouchEvent createObject() {
                return new AbsTouchEvent();
            }

        };
        touchEventPool = new Pool<AbsTouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            AbsTouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = AbsTouchEvent.TOUCH_DOWN;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = AbsTouchEvent.TOUCH_DRAG;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    touchEvent.type = AbsTouchEvent.TOUCH_UP;
                    isTouched = false;
                    break;
            }

            touchEvent.x = touchX = (int)(event.getX() * scaleX);
            touchEvent.y = touchY = (int)(event.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);

            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            if (pointer == 0)
                return isTouched;
            else
                return false;
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized(this){
            return touchX;
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized(this){
            return touchY;
        }
    }

    @Override
    public List<AbsTouchEvent> getTouchEvents() {
        synchronized(this){
            int len = touchEvents.size();
            for (int i = 0; i < len; i++){
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

}
