package com.sunshine.ninjafruit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.tools.ScrollingBackground;

public class MainMenuScreen implements Screen {
    private static final int LOGO_WIDTH = 400, LOGO_HEIGHT = 250, LOGO_Y = NinjaFruitGame.HEIGHT - 500;
    // play now
    private static final int PLAY_BUTTON_WIDTH = 300, PLAY_BUTTON_HEIGHT = 120, PLAY_BUTTON_Y =  NinjaFruitGame.HEIGHT - 500 - 220;
    // easy
    private static final int EASY_BUTTON_WIDTH = 300, EASY_BUTTON_HEIGHT = 120, EASY_BUTTON_Y =  NinjaFruitGame.HEIGHT - 500  - 130 - 220;
    // medium
    private static final int MEDIUM_BUTTON_WIDTH = 300, MEDIUM_BUTTON_HEIGHT = 120, MEDIUM_BUTTON_Y =  NinjaFruitGame.HEIGHT - 500 - 130 - 130 - 220;
    // high
    private static final int HIGH_BUTTON_WIDTH = 300, HIGH_BUTTON_HEIGHT = 120, HIGH_BUTTON_Y =  NinjaFruitGame.HEIGHT - 500 - 130 - 130 - 130 - 220;
    // exit
    private static final int EXIT_BUTTON_WIDTH = 250, EXIT_BUTTON_HEIGHT = 120, EXIT_BUTTON_Y = NinjaFruitGame.HEIGHT - 500 - 130 - 130 - 130 - 130 - 220;

    final NinjaFruitGame game;

    Texture logo, background;
    Texture playNowBtnActive, playNowBtnInactive;
    Texture playEasyBtnActive, playEasyBtnInactive;
    Texture playMediumBtnActive, playMediumBtnInactive;
    Texture playHighBtnActive, playHighBtnInactive;
    Texture exitBtnActive, exitBtnInactive;

    public MainMenuScreen(final NinjaFruitGame game) {
        this.game = game;
        logo = new Texture("fruit-game/logo.jpg"); // logo.png
        background = new Texture("fruit-game/menu-background.jpg");
        // play now
        playNowBtnActive = new Texture("fruit-game/playNow.png");
        playNowBtnInactive = new Texture("fruit-game/playNow.png");
        // level
        playEasyBtnActive = new Texture("fruit-game/easyBtn.png");
        playEasyBtnInactive = new Texture("fruit-game/easyBtn.png");

        playMediumBtnActive = new Texture("fruit-game/mediumBtn.png");
        playMediumBtnInactive = new Texture("fruit-game/mediumBtn.png");

        playHighBtnActive = new Texture("fruit-game/highBtn.png");
        playHighBtnInactive = new Texture("fruit-game/highBtn.png");
        // exit
        exitBtnActive = new Texture("fruit-game/exit.png");
        exitBtnInactive = new Texture("fruit-game/exit.png");

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        final MainMenuScreen mainMenuScreen = this;

        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                // PLAY-NOW game button
                int x = NinjaFruitGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
                if (game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    game.setScreen(new FruitEasyLevelScreen(game));
                }
                // EASY button
                x = NinjaFruitGame.WIDTH / 2 - EASY_BUTTON_WIDTH / 2;
                if (game.cam.getInputInGameWorld().x < x + EASY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < EASY_BUTTON_Y + EASY_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > EASY_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    game.setScreen(new FruitEasyLevelScreen(game));
                }
                // MEDIUM button
                x = NinjaFruitGame.WIDTH / 2 - MEDIUM_BUTTON_WIDTH / 2;
                if (game.cam.getInputInGameWorld().x < x + MEDIUM_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < MEDIUM_BUTTON_Y + MEDIUM_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > MEDIUM_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    game.setScreen(new FruitMediumLevelScreen(game));
                }
                // HIGH button
                x = NinjaFruitGame.WIDTH / 2 - HIGH_BUTTON_WIDTH / 2;
                if (game.cam.getInputInGameWorld().x < x + HIGH_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < HIGH_BUTTON_Y + HIGH_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > HIGH_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    game.setScreen(new FruitHighLevelScreen(game));
                }

                // EXIT button
                x = NinjaFruitGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
                if (game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y) {
                    mainMenuScreen.dispose();
                    Gdx.app.exit();
                }

                return super.touchUp(screenX, screenY, pointer, button);
            }

        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(background, 0, 0, NinjaFruitGame.WIDTH, NinjaFruitGame.HEIGHT);
        game.batch.draw(logo, NinjaFruitGame.WIDTH / 2 - LOGO_WIDTH / 2, LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);
        game.scrollingBackground.updateAndRender(delta, game.batch);

        // play now
        int x = NinjaFruitGame.WIDTH / 2 - PLAY_BUTTON_WIDTH / 2;
        if (game.cam.getInputInGameWorld().x < x + PLAY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > PLAY_BUTTON_Y) {
            game.batch.draw(playNowBtnActive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playNowBtnInactive, x, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }
        // easy
        x = NinjaFruitGame.WIDTH / 2 - EASY_BUTTON_WIDTH / 2;
        if (game.cam.getInputInGameWorld().x < x + EASY_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < EASY_BUTTON_Y + EASY_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > EASY_BUTTON_Y) {
            game.batch.draw(playEasyBtnActive, x, EASY_BUTTON_Y, EASY_BUTTON_WIDTH, EASY_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playEasyBtnInactive, x, EASY_BUTTON_Y, EASY_BUTTON_WIDTH, EASY_BUTTON_HEIGHT);
        }
        // medium
        x = NinjaFruitGame.WIDTH / 2 - MEDIUM_BUTTON_WIDTH / 2;
        if (game.cam.getInputInGameWorld().x < x + MEDIUM_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < MEDIUM_BUTTON_Y + MEDIUM_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > MEDIUM_BUTTON_Y) {
            game.batch.draw(playMediumBtnActive, x, MEDIUM_BUTTON_Y, MEDIUM_BUTTON_WIDTH, MEDIUM_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playMediumBtnInactive, x, MEDIUM_BUTTON_Y, MEDIUM_BUTTON_WIDTH, MEDIUM_BUTTON_HEIGHT);
        }
        // high
        x = NinjaFruitGame.WIDTH / 2 - HIGH_BUTTON_WIDTH / 2;
        if (game.cam.getInputInGameWorld().x < x + HIGH_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < HIGH_BUTTON_Y + HIGH_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > HIGH_BUTTON_Y) {
            game.batch.draw(playHighBtnActive, x, HIGH_BUTTON_Y, HIGH_BUTTON_WIDTH, HIGH_BUTTON_HEIGHT);
        } else {
            game.batch.draw(playHighBtnInactive, x, HIGH_BUTTON_Y, HIGH_BUTTON_WIDTH, HIGH_BUTTON_HEIGHT);
        }
        // exit
        x = NinjaFruitGame.WIDTH / 2 - EXIT_BUTTON_WIDTH / 2;
        if (game.cam.getInputInGameWorld().x < x + EXIT_BUTTON_WIDTH && game.cam.getInputInGameWorld().x > x && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y < EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT && NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y > EXIT_BUTTON_Y) {
            game.batch.draw(exitBtnActive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        } else {
            game.batch.draw(exitBtnInactive, x, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
    }

}
