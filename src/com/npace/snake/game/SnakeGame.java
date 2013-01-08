package com.npace.snake.game;

import com.npace.snake.framework.Screen;
import com.npace.snake.framework.impl.AndroidGame;

public class SnakeGame extends AndroidGame {
    public Screen getStartScreen(){
        return new LoadingScreen(this);
    }
}
