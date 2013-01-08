
package com.npace.snake.game;

import java.util.List;

import android.graphics.Color;

import com.npace.snake.framework.Game;
import com.npace.snake.framework.Graphics;
import com.npace.snake.framework.Input.AbsTouchEvent;
import com.npace.snake.framework.Pixmap;
import com.npace.snake.framework.Screen;

public class GameScreen extends Screen {
    enum GameState {
        READY, RUNNING, PAUSED, GAME_OVER
    }

    GameState state = GameState.READY;
    World world;
    int oldScore = 0;
    String score = "0";

    public GameScreen(Game game) {
        super(game);
        world = new World();
    }

    @Override
    public void update(float deltaTime) {
        List<AbsTouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        if (state == GameState.READY)
            updateReady(touchEvents);
        if (state == GameState.RUNNING)
            updateRunning(touchEvents, deltaTime);
        if (state == GameState.PAUSED)
            updatePaused(touchEvents);
        if (state == GameState.GAME_OVER)
            updateGameOver(touchEvents);
    }

    private void updateReady(List<AbsTouchEvent> touchEvents) {
        if (touchEvents.size() > 0)
            state = GameState.RUNNING;
    }
    
    private void updateRunning(List<AbsTouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; ++i){
            AbsTouchEvent event = touchEvents.get(i);
            if (event.type == AbsTouchEvent.TOUCH_UP){
                if (event.x < 64 && event.y < 64){
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    state = GameState.PAUSED;
                    return;
                }
            }
            if (event.type == AbsTouchEvent.TOUCH_DOWN){
                if (event.x < 64 && event.y > 416)
                    world.snake.turnLeft();
                if (event.x > 256 && event.y > 416)
                    world.snake.turnRight();
            }
        }
        world.update(deltaTime);
        if (world.gameOver){
            if (Settings.soundEnabled)
                Assets.bitten.play(1);
            state = GameState.GAME_OVER;
        }
        if (oldScore != world.score){
            oldScore = world.score;
            score = "" + oldScore;
            if (Settings.soundEnabled){
                Assets.eat.play(1);
            }
        }
    }
    
    private void updatePaused(List<AbsTouchEvent> touchEvents){
        int len = touchEvents.size();
        for (int i = 0; i < len; ++i){
            AbsTouchEvent event = touchEvents.get(i);
            if (event.type == AbsTouchEvent.TOUCH_UP){
                if (event.x > 80 && event.x <= 240){
                    if (event.y > 100 && event.y < 148){
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                        state = GameState.RUNNING;
                        return;
                    }
                    if (event.y > 148 && event.y < 196){
                        if (Settings.soundEnabled)
                            Assets.click.play(1);
                        game.setScreen(new MainMenuScreen(game));
                        return;
                    }
                }
            }
        }
    }
    
    private void updateGameOver(List<AbsTouchEvent> touchEvents){
        int len = touchEvents.size();
        for (int i = 0; i < len; ++i){
            AbsTouchEvent event = touchEvents.get(i);
            if (event.type == AbsTouchEvent.TOUCH_UP){
                if (event.x >= 128 && event.x <= 192 &&
                        event.y >= 200 && event.y <= 264){
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.background, 0, 0);
        drawWorld(world);
        if (state == GameState.READY)
            drawReadyUI();
        if (state == GameState.RUNNING)
            drawRunningUI();
        if (state == GameState.PAUSED)
            drawPausedUI();
        if (state == GameState.GAME_OVER)
            drawGameOverUI();
        
        drawText(g, score, g.getWidth()/2 - score.length() * 20 / 2, g.getHeight() - 42);
    }
    
    private void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; ++i){
            char character = line.charAt(i);
            
            if (character == ' '){
                x += 20;
                continue;
            }
            
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.'){
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }
            
            g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    private void drawGameOverUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.gameOver, 62, 100);
        g.drawPixmap(Assets.buttons, 128, 200, 0, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawPausedUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.pause, 80, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawRunningUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.buttons, 0, 0, 64, 128, 64, 64);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }

    private void drawReadyUI() {
        Graphics g = game.getGraphics();
        
        g.drawPixmap(Assets.ready, 47, 100);
        g.drawLine(0, 416, 480, 416, Color.BLACK);
    }

    private void drawWorld(World world){
        Graphics g = game.getGraphics();
        Snake snake = world.snake;
        SnakePart head = snake.parts.get(0);
        Stain stain = world.stain;
        
        Pixmap stainPixmap = null;
        if (stain.type == Stain.TYPE_1)
            stainPixmap = Assets.stain1;
        if (stain.type == Stain.TYPE_2)
            stainPixmap = Assets.stain2;
        if (stain.type == Stain.TYPE_3)
            stainPixmap = Assets.stain3;
        
        int x = stain.x * 32;
        int y = stain.y * 32;
        g.drawPixmap(stainPixmap, x, y);
        
        int len = snake.parts.size();
        for (int i = 1; i < len; ++i){
            SnakePart part = snake.parts.get(i);
            x = part.x * 32;
            y = part.y * 32;
            g.drawPixmap(Assets.tail, x, y);
        }
        
        Pixmap headPixmap = null;
        if (snake.direction == Snake.UP)
            headPixmap = Assets.headUp;
        if (snake.direction == Snake.LEFT)
            headPixmap = Assets.headLeft;
        if (snake.direction == Snake.DOWN)
            headPixmap = Assets.headDown;
        if (snake.direction == Snake.RIGHT)
            headPixmap = Assets.headRight;
        x = head.x * 32 + 16;
        y = head.y * 32 + 16;
        g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);
    }

    @Override
    public void pause() {
        if (state == GameState.RUNNING)
            state = GameState.PAUSED;
        
        if (world.gameOver){
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
