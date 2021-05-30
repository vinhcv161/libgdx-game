package com.sunshine.ninjafruit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Align;
import com.sunshine.ninjafruit.NinjaFruitGame;
import com.sunshine.ninjafruit.tools.ScrollingBackground;
import com.sunshine.ninjafruit.tools.Utils;

public class GameOverScreen implements Screen {

    private static final int BANNER_WIDTH = 600;
    private static final int BANNER_HEIGHT = 230;

    NinjaFruitGame game;

    int score, highscore;

    Texture gameOverBanner;
    BitmapFont textFont;

    public GameOverScreen(NinjaFruitGame game, int score) {
        this.game = game;
        this.score = score;

        //Get highscore from save file
        Preferences prefs = Gdx.app.getPreferences("NinjaFruitCache");
        this.highscore = prefs.getInteger("highscore", 0);

        //Check if score beats highscore
        if (score > highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        //Load textures and fonts
        gameOverBanner = new Texture("game_over.png");
        textFont = Utils.createFont("fonts/robotobold.ttf", Color.WHITE, 76, Color.ROYAL, 2f);

        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(ScrollingBackground.DEFAULT_SPEED);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        game.batch.draw(gameOverBanner, NinjaFruitGame.WIDTH / 2 - BANNER_WIDTH / 2, NinjaFruitGame.HEIGHT - BANNER_HEIGHT - 45, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(textFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highscoreLayout = new GlyphLayout(textFont, "Highscore: \n" + highscore, Color.WHITE, 0, Align.left, false);
        textFont.draw(game.batch, scoreLayout, NinjaFruitGame.WIDTH / 2 - scoreLayout.width / 2, NinjaFruitGame.HEIGHT - BANNER_HEIGHT - 45 * 2);
        textFont.draw(game.batch, highscoreLayout, NinjaFruitGame.WIDTH / 2 - highscoreLayout.width / 2, NinjaFruitGame.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 45 * 3);

        float touchX = game.cam.getInputInGameWorld().x, touchY = NinjaFruitGame.HEIGHT - game.cam.getInputInGameWorld().y;

        GlyphLayout tryAgainLayout = new GlyphLayout(textFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(textFont, "Main Menu");

        float tryAgainX = NinjaFruitGame.WIDTH / 2 - tryAgainLayout.width / 2;
        float tryAgainY = NinjaFruitGame.HEIGHT / 2 - tryAgainLayout.height / 2 - 45;
        float mainMenuX = NinjaFruitGame.WIDTH / 2 - mainMenuLayout.width / 2;
        float mainMenuY = NinjaFruitGame.HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 45 * 2;

        //Checks if hovering over try again button
        if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
            tryAgainLayout.setText(textFont, "Try Again", Color.YELLOW, 0, Align.left, false);

        //Checks if hovering over main menu button
        if (touchX >= mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY >= mainMenuY - mainMenuLayout.height && touchY < mainMenuY)
            mainMenuLayout.setText(textFont, "Main Menu", Color.YELLOW, 0, Align.left, false);

        //If try again and main menu is being pressed
        if (Gdx.input.isTouched()) {
            //Try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new FruitEasyLevelScreen(game));
                return;
            }

            //main menu
            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }

        //Draw buttons
        textFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        textFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

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
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(null);
        textFont.dispose();
    }

}
