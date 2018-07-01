package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Splash implements Screen {
    private SpriteBatch spriteBatch;
    private Texture splsh;
    private Game myGame;
    private float elapsed;
    private Camera camera;
    private Viewport viewport;
    private int WIDTH = 480;
    private int HEIGHT = 640;

    public Splash(Game g)
    {
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WIDTH, HEIGHT, camera);
        myGame = g;
    }

    @Override
    public void render(float delta)
    {
        spriteBatch.setProjectionMatrix(camera.projection);
        spriteBatch.setTransformMatrix(camera.view);
        elapsed += delta;

        if (elapsed>2.0){
            myGame.setScreen(new GameScreen());
        } else {
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            spriteBatch.begin();
            spriteBatch.draw(splsh, 0, 0);
            spriteBatch.end();
        }

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

    }

    @Override
    public void show()
    {
        spriteBatch = new SpriteBatch();
        splsh = new Texture(Gdx.files.internal("back-2.png"));
    }

}
