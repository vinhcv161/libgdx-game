package com.sunshine.ninjafruit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sunshine.ninjafruit.screens.menu.MainMenuScreen;
import com.sunshine.ninjafruit.tools.GameCamera;
import com.sunshine.ninjafruit.tools.ScrollingBackground;

public class NinjaFruitGame extends Game {
	public static int WIDTH;
	public static int HEIGHT;
	
	public SpriteBatch batch;
	public ScrollingBackground scrollingBackground;
	public GameCamera cam;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		cam = new GameCamera(WIDTH, HEIGHT);

		this.scrollingBackground = new ScrollingBackground();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(cam.combined());
		super.render();
	}
	
	@Override
	public void resize(int width, int height) {
		cam.update(width, height);
		super.resize(width, height);
	}
	
}
