
package com.npace.snake.framework.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

import com.npace.snake.framework.Input.AbsKeyEvent;
import com.npace.snake.framework.Pool;
import com.npace.snake.framework.Pool.PoolObjectFactory;

public class KeyboardHandler implements OnKeyListener {
    boolean[] pressedKeys = new boolean[128];
    Pool<AbsKeyEvent> keyEventPool;
    List<AbsKeyEvent> keyEventsBuffer = new ArrayList<AbsKeyEvent>();
    List<AbsKeyEvent> keyEvents = new ArrayList<AbsKeyEvent>();

    public KeyboardHandler(View view) {
        PoolObjectFactory<AbsKeyEvent> factory = new PoolObjectFactory<AbsKeyEvent>() {

            @Override
            public AbsKeyEvent createObject() {
                return new AbsKeyEvent();
            }
        };
        keyEventPool = new Pool<AbsKeyEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return false;

        synchronized (this) {
            AbsKeyEvent keyEvent = keyEventPool.newObject();
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char)event.getUnicodeChar();
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                keyEvent.type = AbsKeyEvent.KEY_DOWN;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys[keyCode] = true;
                }
            }
            if (event.getAction() == KeyEvent.ACTION_UP) {
                keyEvent.type = AbsKeyEvent.KEY_UP;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys[keyCode] = false;
                }
            }
            keyEventsBuffer.add(keyEvent);
        }
        return false;
    }
    
    public boolean isKeyPressed(int keyCode){
        if (keyCode < 0 || keyCode > 127) return false;
        return pressedKeys[keyCode];
    }
    
    public List<AbsKeyEvent> getKeyEvents(){
        synchronized (this) {
            int len = keyEvents.size();
            for (int i = 0; i < len; i++){
                keyEventPool.free(keyEvents.get(i));
            }
            keyEvents.clear();
            keyEvents.addAll(keyEventsBuffer);
            keyEventsBuffer.clear();
            return keyEvents;
        }
    }
}
