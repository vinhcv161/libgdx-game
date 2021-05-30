package com.sunshine.ninjafruit.screens.menu;

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
import com.sunshine.ninjafruit.entities.ImagePosition;
import com.sunshine.ninjafruit.entities.TextPosition;
import com.sunshine.ninjafruit.screens.game.FruitEasyLevelScreen;
import com.sunshine.ninjafruit.tools.Constants.CacheKey;
import com.sunshine.ninjafruit.tools.ScrollingBackground;
import com.sunshine.ninjafruit.tools.Utils;

public class GameOverScreen implements Screen {
    NinjaFruitGame game;

    private float radius;

    int score, highscore;

    ImagePosition gameOverBanner;
    TextPosition scoreLayout, highScoreLayout, tryAgain, mainMenu;
    BitmapFont textFont;

    public GameOverScreen(NinjaFruitGame game, int score) {
        this.game = game;
        this.score = score;
        radius = Math.max(NinjaFruitGame.HEIGHT, NinjaFruitGame.WIDTH) / 18f;
        textFont = Utils.createFont("fonts/robotobold.ttf", Color.WHITE, (int) (0.65f * radius), Color.ROYAL, 2f);

        //Get highscore from save file
        Preferences prefs = Gdx.app.getPreferences(CacheKey.APP_CACHE_KEY);
        this.highscore = prefs.getInteger(CacheKey.HIGH_SCORE_KEY, 0);

        //Check if score beats highscore
        if (score > highscore) {
            prefs.putInteger(CacheKey.HIGH_SCORE_KEY, score);
            prefs.flush();
        }

        //Load textures and fonts
        gameOverBanner = new ImagePosition(new Texture("game_over.png"), NinjaFruitGame.WIDTH / 2 - 1.4f * radius, NinjaFruitGame.HEIGHT - 1.5f * radius, 2.8f * radius, 1.5f * radius);

        float padding = 1.5f * radius / 2;
        GlyphLayout layout = new GlyphLayout(textFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        padding += layout.height + radius / 2;
        scoreLayout = new TextPosition(layout, NinjaFruitGame.WIDTH / 2 - layout.width / 2, NinjaFruitGame.HEIGHT - padding);
        System.out.println((NinjaFruitGame.WIDTH / 2 - layout.width / 2) + " - " + (NinjaFruitGame.HEIGHT - padding));

        layout = new GlyphLayout(textFont, "Highscore: \n" + highscore, Color.WHITE, 0, Align.left, false);
        padding += layout.height + radius / 2;
        highScoreLayout = new TextPosition(layout, NinjaFruitGame.WIDTH / 2 - layout.width / 2, NinjaFruitGame.HEIGHT - padding);
        System.out.println((NinjaFruitGame.WIDTH / 2 - layout.width / 2) + " - " + (NinjaFruitGame.HEIGHT - padding));

        layout = new GlyphLayout(textFont, "Try Again", Color.WHITE, 0, Align.left, false);
        padding += layout.height + 1.75f * radius;
        tryAgain = new TextPosition(layout, NinjaFruitGame.WIDTH / 2 - layout.width / 2, NinjaFruitGame.HEIGHT - padding);
        System.out.println((NinjaFruitGame.WIDTH / 2 - layout.width / 2) + " - " + (NinjaFruitGame.HEIGHT - padding));

        layout = new GlyphLayout(textFont, "Main Menu", Color.WHITE, 0, Align.left, false);
        padding += layout.height + radius / 2;
        mainMenu = new TextPosition(layout, NinjaFruitGame.WIDTH / 2 - layout.width / 2, NinjaFruitGame.HEIGHT - padding);
        System.out.println((NinjaFruitGame.WIDTH / 2 - layout.width / 2) + " - " + (NinjaFruitGame.HEIGHT - padding));

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

        drawImage(gameOverBanner);
        drawText(scoreLayout);
        drawText(highScoreLayout);

        //If try again and main menu is being pressed
        if (Gdx.input.isTouched()) {
            float touchX = game.cam.getInputInGameWorld().x, touchY = game.cam.getInputInGameWorld().y;
            //Try again
            if (tryAgain.isClicked(touchX, touchY)) {
                this.dispose();
                game.batch.end();
                game.setScreen(new FruitEasyLevelScreen(game));
                return;
            }
            //main menu
            else if (mainMenu.isClicked(touchX, touchY)) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
        }
        //Draw buttons
        drawText(tryAgain);
        drawText(mainMenu);

        game.batch.end();
    }

    private void drawImage(ImagePosition pos) {
        game.batch.draw(pos.text, pos.x, pos.y, pos.width, pos.height);
    }

    private void drawText(TextPosition txt) {
        textFont.draw(game.batch, txt.layout, txt.x, txt.y);
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
