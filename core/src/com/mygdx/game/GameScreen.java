package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import java.util.ArrayList;


/**
 * Created by Edgar on 25/05/18.
 */

public class GameScreen extends ScreenAdapter implements Screen{

    //inisialisasi
    private static final float WIDTH = 480;
    private static final float HEIGHT = 640;
    private Viewport viewPort;
    private Camera camera;
    SpriteBatch batch;
    private Texture background,logo;
    private Texture gambarTikus;
    private Texture gambarKelinci;

    private Boolean mode = false;

    //Button
    private Texture play,score,quit;
    private Stage stage;
    private ImageButton buttonPlay,buttonScore,buttonQuit;
    private TextureRegionDrawable playRegionDrawable,scoreRegionDrawable,quitRegionDrawable;
    private ShapeRenderer shape;

    //frame grid
    private Rectangle grid;
    private Rectangle grid1,grid2,grid3,grid4,grid5,grid6,grid7,grid8,grid9;
    private Array<Rectangle> arrayGrid;

    // random
    private float randomKemunculan;
    private ArrayList<Float> arrayJenis;
    private float randomJenis;

    //tikus
    private Tikus tikus;
    private ArrayList<Tikus> arrayTikus;

    //kelinci
    private Kelinci kelinci;
    private ArrayList<Kelinci> arrayKelinci;

    @Override
    public void show(){
        camera = new OrthographicCamera();
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        viewPort = new FitViewport(WIDTH, HEIGHT, camera);

        arrayTikus = new ArrayList<Tikus>();
        arrayKelinci = new ArrayList<Kelinci>();

        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("bg.png"));
        logo = new Texture(Gdx.files.internal("logo.png"));

        //button main menu
        play = new Texture(Gdx.files.internal("button-play.png"));
        playRegionDrawable = new TextureRegionDrawable(new TextureRegion(play));
        //button score
        score = new Texture(Gdx.files.internal("button-play.png"));
        scoreRegionDrawable = new TextureRegionDrawable(new TextureRegion(score));
        //button quit
        quit = new Texture(Gdx.files.internal("button-play.png"));
        quitRegionDrawable = new TextureRegionDrawable(new TextureRegion(quit));
        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        //tikus
        gambarTikus = new Texture(Gdx.files.internal("mouse.png"));

        //kelinci
        gambarKelinci = new Texture(Gdx.files.internal("bunny.png"));

        arrayJenis = new ArrayList<Float>();

        //frame grid
        this.grid = new Rectangle(Gdx.graphics.getWidth()/2-300f/2,90,300f,300f);
        arrayGrid = new Array<Rectangle>();

        for (int i=1;i<=3;i++){
            for (int j=1;j<=3;j++){
                Rectangle rect = new Rectangle(i * 100, j * 100, 80f, 80f);
                arrayGrid.add(rect);
            }
        }

        randomKemunculan = MathUtils.random(8) + 1;

        ArrayList<Float> arrayPosisi = new ArrayList<Float>();
        for (int i = 0; i< randomKemunculan; i++) {
            float randomPosisi = 0;

                while(true) {
                    randomPosisi = MathUtils.random(8) + 1;
                    if (arrayPosisi.size() > 0) {

                        boolean find = check(arrayPosisi, randomPosisi);
                        if (find == false)
                            break;
                    }
                    else {
                        break;
                    }
                }

            arrayPosisi.add(randomPosisi);


        }

        for (int i =0; i<arrayPosisi.size(); i++) {
            randomJenis = MathUtils.random(1);
            arrayJenis.add(randomJenis);
        }

        for(int i=0; i<arrayPosisi.size(); i++) {
            if (arrayJenis.get(i) == 0.0) {
                tikus = new Tikus(gambarTikus, arrayGrid.get(Math.round(arrayPosisi.get(i) - 1)));
                arrayTikus.add(tikus);
            }
            else {
                kelinci = new Kelinci(gambarKelinci, arrayGrid.get(Math.round(arrayPosisi.get(i) -1)));
                arrayKelinci.add(kelinci);
            }
        }

        for(float f : arrayPosisi) {
            System.out.println(f);
        }
        System.out.println();
        for(float f : arrayJenis) {
            System.out.println(f);
        }
    }

    private boolean check(ArrayList<Float> arrayPosisi, float value) {
        for(float f : arrayPosisi) {
            if (f == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(float delta){

        shape = new ShapeRenderer();

        batch.begin();

        if (!mode){

            //batch.draw(background,0,0);
            buatButtonPlay();
            buatButtonScore();
            buatButtonQuit();
            stage.draw();
            buttonPlay.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    mode=true;
                }
            });
            buttonScore.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    //mode=false;
                }
            });
            buttonQuit.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    //mode=false;
                }
            });
            batch.end();
        } else {
            batch.draw(background,0,0);
            for (int i=0; i<arrayTikus.size(); i++) {
                arrayTikus.get(i).drawTexture(batch);
            }
            for (int i=0; i<arrayKelinci.size(); i++) {
                arrayKelinci.get(i).drawTexture(batch);
            }
            batch.end();

            shape.setProjectionMatrix(camera.projection);
            shape.setTransformMatrix(camera.view);
            shape.begin(ShapeRenderer.ShapeType.Line);
            //draw shape
            shape.rect(grid.x,grid.y,grid.width,grid.height);
            for (int i=0;i<9;i++){
                Rectangle r = arrayGrid.get(i);
                shape.rect(r.x, r.y, r.width, r.height);
            }
            shape.end();
        }

    }

    private void buatButtonPlay() {
        buttonPlay = new ImageButton(playRegionDrawable);
        buttonPlay.setPosition(150, 240);
        buttonPlay.setSize(180f,180f);
        stage.addActor(buttonPlay);
    }

    private void buatButtonScore() {
        buttonScore = new ImageButton(scoreRegionDrawable);
        buttonScore.setPosition(150, 140);
        buttonScore.setSize(180f,180f);
        stage.addActor(buttonScore);
    }

    private void buatButtonQuit() {
        buttonQuit = new ImageButton(quitRegionDrawable);
        buttonQuit.setPosition(150, 40);
        buttonQuit.setSize(180f,180f);
        stage.addActor(buttonQuit);
    }

    private void drawFrameGrid(){

    }
}
