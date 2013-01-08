
package com.npace.snake.game;

import java.util.List;

import com.npace.snake.framework.Game;
import com.npace.snake.framework.Graphics;
import com.npace.snake.framework.Screen;
import com.npace.snake.framework.Input.AbsTouchEvent;

public class HelpScreen2 extends Screen {

    public HelpScreen2(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<AbsTouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            AbsTouchEvent event = touchEvents.get(i);
            if (event.type == AbsTouchEvent.TOUCH_UP) {
                if (event.x > 256 && event.y > 416) {
                    game.setScreen(new HelpScreen3(game));
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
                if (event.x > 0 && event.x <= 64 && event.y > 416) {
                    game.setScreen(new HelpScreen(game));
                    if (Settings.soundEnabled)
                        Assets.click.play(1);
                    return;
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.help2, 64, 100);
        g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
        g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
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
