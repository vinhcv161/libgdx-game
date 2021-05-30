package com.sunshine.ninjafruit.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.entities.ImagePosition;
import com.sunshine.ninjafruit.tools.Constants;
import com.sunshine.ninjafruit.tools.ScrollingBackground;

public class LevelSelectionScreen implements Screen {
    final NinjaFruitGame game;

    private float radius;

    Texture background;
    ImagePosition logo;
    ImagePosition playEasyBtn, playMediumBtn, playHighBtn, exitBtn;

    public LevelSelectionScreen(final NinjaFruitGame game) {
        this.game = game;
        radius = Math.max(NinjaFruitGame.HEIGHT, NinjaFruitGame.WIDTH) / 18f;

        float widthBtn = 2.8f * radius;
        float heightBtn = 1.5f * radius;
        float paddingBtn = heightBtn + radius / 10;
        float xBtn = NinjaFruitGame.WIDTH / 2 - widthBtn / 2;
        float yBtn = NinjaFruitGame.HEIGHT - paddingBtn - heightBtn / 2;

        background = new Texture("fruit-game/menu-background.jpg");

        int i = 0;
        // o.25f = 0.5f - 1.5f/2;
        logo = new ImagePosition(new Texture("fruit-game/logo.jpg"), xBtn - 0.25f * widthBtn, yBtn - i++ * paddingBtn, widthBtn * 1.5f, heightBtn * 1.5f);

        playEasyBtn = new ImagePosition(new Texture("fruit-game/easyBtn.png"), xBtn, yBtn - i++ * paddingBtn, widthBtn, heightBtn);
        playMediumBtn = new ImagePosition(new Texture("fruit-game/mediumBtn.png"), xBtn, yBtn - i++ * paddingBtn, widthBtn, heightBtn);
        playHighBtn = new ImagePosition(new Texture("fruit-game/highBtn.png"), xBtn, yBtn - i++ * paddingBtn, widthBtn, heightBtn);
        exitBtn = new ImagePosition(new Texture("fruit-game/exit.png"), xBtn, yBtn - i++ * paddingBtn, widthBtn, heightBtn);

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);

        final Preferences prefs = Gdx.app.getPreferences(Constants.CacheKey.APP_CACHE_KEY);
        final LevelSelectionScreen levelScreen = this;
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float x = game.cam.getInputInGameWorld().x;
                float y = game.cam.getInputInGameWorld().y;

                // EASY button
                if (playEasyBtn.isClicked(x, y)) {
                    prefs.putInteger(Constants.CacheKey.LEVEL_KEY, Constants.Level.EASY_LEVEL_KEY);
                    prefs.flush();

                    levelScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                // MEDIUM button
                else if (playMediumBtn.isClicked(x, y)) {
                    prefs.putInteger(Constants.CacheKey.LEVEL_KEY, Constants.Level.MEDIUM_LEVEL_KEY);
                    prefs.flush();

                    levelScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                // HIGH button
                else if (playHighBtn.isClicked(x, y)) {
                    prefs.putInteger(Constants.CacheKey.LEVEL_KEY, Constants.Level.HIGH_LEVEL_KEY);
                    prefs.flush();

                    levelScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                // EXIT button
                else if (exitBtn.isClicked(x, y)) {
                    levelScreen.dispose();
                    game.setScreen(new MainMenuScreen(game));
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
        game.scrollingBackground.updateAndRender(delta, game.batch);

        //
        drawPos(logo);
        drawPos(playEasyBtn);
        drawPos(playMediumBtn);
        drawPos(playHighBtn);
        drawPos(exitBtn);

        game.batch.end();
    }

    private void drawPos(ImagePosition pos) {
        game.batch.draw(pos.text, pos.x, pos.y, pos.width, pos.height);
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
