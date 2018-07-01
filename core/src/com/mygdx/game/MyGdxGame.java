package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyGdxGame extends Game {
	private Viewport viewport;
	private Camera camera;

	public void create() {
		camera = new PerspectiveCamera();
		viewport = new FitViewport(480, 640, camera);
		this.setScreen(new Splash(this));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
