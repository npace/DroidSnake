
package com.npace.snake.game;

import java.util.List;

import com.npace.snake.framework.Game;
import com.npace.snake.framework.Graphics;
import com.npace.snake.framework.Input.AbsTouchEvent;
import com.npace.snake.framework.Screen;

public class HighscoreScreen extends Screen {
    String[] lines = new String[5];

    public HighscoreScreen(Game game) {
        super(game);

        for (int i = 0; i < lines.length; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highScores[i];
        }
    }

    @Override
    public void update(float deltaTime) {
        List<AbsTouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            AbsTouchEvent event = touchEvents.get(i);
            if (event.type == AbsTouchEvent.TOUCH_UP) {
                if (event.x < 64 && event.y > 416) {
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42);

        int y = 100;
        for (int i = 0; i < 5; i++) {
            drawText(g, lines[i], 20, y);
            y += 50;
        }

        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
    }

    public void drawText(Graphics g, String line, int x, int y) {
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            if (character == ' ') {
                x += 20;
                continue;
            }
            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
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

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
