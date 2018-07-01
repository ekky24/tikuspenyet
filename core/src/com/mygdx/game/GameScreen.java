package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.View;


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
    private Texture backgrid;
    private Texture backgrid2;
    private Texture backbottom;
    private Texture backs;

    private int mode = 0;

    //Button
    private Texture play,score,quit,gameOvers, splash, kembali;
    private Stage stage;
    private ImageButton buttonPlay,buttonScore,buttonQuit,buttonGameOver, buttonKembali;
    private TextureRegionDrawable playRegionDrawable,scoreRegionDrawable,quitRegionDrawable,gameOverRegionDrawable,kembaliRegionDrawable;
    private ShapeRenderer shape;

    //frame grid
    private Rectangle grid;
    private Rectangle grid1,grid2,grid3,grid4,grid5,grid6,grid7,grid8,grid9;
    private Array<Rectangle> arrayGrid;

    // random
    private float randomKemunculan;
    private float randomJenis;

    //tikus
    private Tikus tikus;
    private ArrayList<Tikus> arrayTikus;

    //kelinci
    private Kelinci kelinci;
    private ArrayList<Kelinci> arrayKelinci;

    private float elapsedSembunyi1 = 0;
    private float elapsedSembunyi2 = 1.0f;
    private float elapsedRonde = 0;

    private ArrayList<Float> arrayPosisi;
    private ArrayList<Float> arrayPosisi2;

    //music and sound
    private Music backSound;
    private Sound sound_button;
    private Sound sound_kelinci;
    private Sound sound_tikus;

    //gameover
    private boolean gameOver = false;

    // Font
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter param;
    private static GlyphLayout glyphLayout = new GlyphLayout();
    BitmapFont font;
    BitmapFont font_score;
    BitmapFont font_score_real_time;
    BitmapFont font_game_over;

    // score
    int score_real_time = 0;

    int difficulty = 1;

    // High score
    Preferences pref;

    private float elapsedTahan = 0;

    static final String gameOverText = "Game Over";

    @Override
    public void show(){

        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(WIDTH / 2, HEIGHT / 2, 0);
        camera.update();
        viewPort = new FitViewport(WIDTH, HEIGHT, camera);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("MotionControl-Bold.otf"));
        param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size = 50;
        param.borderColor = Color.BLACK;
        param.borderWidth = 3;
        param.shadowColor = Color.BLACK;
        param.color = Color.WHITE;

        font = generator.generateFont(param);
        param.size = 40;
        font_game_over = generator.generateFont(param);
        font_score = generator.generateFont(param);
        param.size = 30;
        param.borderWidth = 1;
        font_score_real_time = generator.generateFont(param);
        generator.dispose();

        sound_button = Gdx.audio.newSound(Gdx.files.internal("button.mp3"));
        sound_kelinci = Gdx.audio.newSound(Gdx.files.internal("kelinci.wav"));
        sound_tikus = Gdx.audio.newSound(Gdx.files.internal("tikus.mp3"));

        arrayTikus = new ArrayList<Tikus>();
        arrayKelinci = new ArrayList<Kelinci>();

        batch = new SpriteBatch();

        background = new Texture(Gdx.files.internal("bg.png"));
        logo = new Texture(Gdx.files.internal("logo.png"));
        backgrid = new Texture(Gdx.files.internal("back1.png"));
        backgrid2 = new Texture(Gdx.files.internal("backgrid2.png"));
        backbottom = new Texture(Gdx.files.internal("backBottom.png"));
        backs = new Texture(Gdx.files.internal("back12.png"));
        splash = new Texture(Gdx.files.internal("back-2.png"));

        pref = Gdx.app.getPreferences("High Score");

        //button main menu
        play = new Texture(Gdx.files.internal("button-play.png"));
        playRegionDrawable = new TextureRegionDrawable(new TextureRegion(play));
        //button score
        score = new Texture(Gdx.files.internal("button-score.png"));
        scoreRegionDrawable = new TextureRegionDrawable(new TextureRegion(score));
        //button quit
        quit = new Texture(Gdx.files.internal("button-quit.png"));
        quitRegionDrawable = new TextureRegionDrawable(new TextureRegion(quit));
        // button kembali
        kembali = new Texture(Gdx.files.internal("kembali.png"));
        kembaliRegionDrawable = new TextureRegionDrawable(new TextureRegion(kembali));
        //button gameOver
        gameOvers = new Texture(Gdx.files.internal("button-menu.png"));
        gameOverRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameOvers));

        stage = new Stage(viewPort);
        Gdx.input.setInputProcessor(stage);

        //music and sound
        backSound = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        //tapTikus = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
        //tapKelinci = Gdx.audio.newSound(Gdx.files.internal("hit2.mp3"));


        //tikus
        gambarTikus = new Texture(Gdx.files.internal("mouse.png"));

        //kelinci
        gambarKelinci = new Texture(Gdx.files.internal("bunny.png"));

        //frame grid
        this.grid = new Rectangle(WIDTH/2-300f/2,90,300f,300f);
        arrayGrid = new Array<Rectangle>();
        arrayPosisi = new ArrayList<Float>();
        arrayPosisi2 = new ArrayList<Float>();
    }

    private void rondeBaru() {
        for (int i=1;i<=3;i++){
            for (int j=1;j<=3;j++){
                Rectangle rect = new Rectangle(i * 100, j * 100, 80f, 80f);
                arrayGrid.add(rect);
            }
        }

        if (score_real_time > 50) {
            difficulty = 2;
        }
        else if (score_real_time > 100) {
            difficulty = 3;
        }

        randomKemunculan = MathUtils.random(8) + 1;
        elapsedTahan = 0;

        for (int i = 0; i< randomKemunculan; i++) {
            float randomPosisi = 0;

            while(true) {
                randomPosisi = MathUtils.random(8) + 1;
                boolean find = check(Math.round(randomPosisi));
                if (find == false) {
                    randomJenis = MathUtils.random(1);
                    if(randomJenis == 0.0) {
                        tikus = new Tikus(gambarTikus);
                        tikus.setIndex(Math.round(randomPosisi));
                        tikus.setRect(arrayGrid.get(Math.round(randomPosisi)-1));
                        tikus.setDiff(difficulty);
                        arrayTikus.add(tikus);
                    }
                    else {
                        kelinci = new Kelinci(gambarKelinci);
                        kelinci.setIndex(Math.round(randomPosisi));
                        kelinci.setRect(arrayGrid.get(Math.round(randomPosisi)-1));
                        kelinci.setDiff(difficulty);
                        arrayKelinci.add(kelinci);
                    }
                    break;
                }
            }

            arrayPosisi.add(randomPosisi);
        }
        for (int i=0; i<arrayPosisi.size(); i++){
            arrayPosisi2.add(arrayPosisi.get(i));
        }
        ArrayList<Float> arrayTemp = new ArrayList<Float>();

        for (float f: arrayPosisi2){
            if (f == 3.0 || f == 6.0 || f == 9.0) {
                arrayTemp.add(f);
            }
        }
        for (float f: arrayPosisi2){
            if (f == 2.0 || f == 5.0 || f == 8.0) {
                arrayTemp.add(f);
            }
        }
        for (float f: arrayPosisi2){
            if (f == 1.0 || f == 4.0 || f == 7.0) {
                arrayTemp.add(f);
            }
        }
        arrayPosisi2.clear();
        for (float f: arrayTemp){
            arrayPosisi2.add(f);
        }
        arrayPosisi.clear();

    }

    private boolean check(int value) {
        for(int i=0; i<arrayTikus.size(); i++) {
            if (arrayTikus.get(i).index == value) {
                return true;
            }
        }
        for(int i=0; i<arrayKelinci.size(); i++) {
            if (arrayKelinci.get(i).index == value) {
                return true;
            }
        }
        for(int i=0; i<arrayPosisi.size(); i++) {
            if (arrayPosisi.get(i) == (float) value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        backSound.setVolume(0.7f);
        backSound.play();

        shape = new ShapeRenderer();

        batch.begin();

        if (mode==0){
            batch.draw(splash,0,0);
            gameOver = false;
            //batch.draw(background,0,0);
            buatButtonPlay();
            buatButtonScore();
            buatButtonQuit();

            if(Gdx.input.isTouched()) {
                Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                camera.unproject(tmp);
                Rectangle btnPlay= new Rectangle(buttonPlay.getX(), buttonPlay.getY(), buttonPlay.getWidth(), buttonPlay.getHeight());
                Rectangle btnScore= new Rectangle(buttonScore.getX(), buttonScore.getY(), buttonScore.getWidth(), buttonScore.getHeight());
                Rectangle btnExit= new Rectangle(buttonQuit.getX(), buttonQuit.getY(), buttonQuit.getWidth(), buttonQuit.getHeight());

                if(btnPlay.contains(tmp.x,tmp.y))
                {
                    sound_button.play(0.8f);
                    mode = 1;
                    rondeBaru();
                }
                if(btnScore.contains(tmp.x,tmp.y))
                {
                    sound_button.play(0.8f);
                    mode = 2;
                }
                if(btnExit.contains(tmp.x,tmp.y))
                {
                    sound_button.play(0.8f);
                    Gdx.app.exit();
                }
            }

            batch.end();
        }
        else if(mode == 2) {
            ArrayList<Integer> score = readPref();
            buttonPlay.clearListeners();
            buttonScore.clearListeners();
            buttonQuit.clearListeners();

            batch.draw(splash,0,0);

            glyphLayout.setText(font, "High Score: ");
            font.draw(batch, glyphLayout, WIDTH / 2 - glyphLayout.width / 2, 350);

            buatButtonKembali();
            //stage.draw();

            buttonKembali.draw(batch, 1);

            if(Gdx.input.isTouched()) {
                Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                camera.unproject(tmp);
                Rectangle textureBounds= new Rectangle(buttonKembali.getX(), buttonKembali.getY(), buttonKembali.getWidth(), buttonKembali.getHeight());

                if(textureBounds.contains(tmp.x,tmp.y))
                {
                    sound_button.play(0.8f);
                    mode = 0;
                }
            }

            glyphLayout.setText(font_score, "1. " + score.get(0));
            font_score.draw(batch, "1. " + score.get(0), WIDTH / 2 - glyphLayout.width / 2, 280);
            font_score.draw(batch, "2. " + score.get(1), WIDTH / 2 - glyphLayout.width / 2, 230);
            font_score.draw(batch, "3. " + score.get(2), WIDTH / 2 - glyphLayout.width / 2, 180);
            font_score.draw(batch, "4. " + score.get(3), WIDTH / 2 - glyphLayout.width / 2, 130);
            font_score.draw(batch, "5. " + score.get(4), WIDTH / 2 - glyphLayout.width / 2, 80);
            batch.end();
        }
        else if(mode == 1) {

            int cArrTikus = 0;
                int cArrKelinci = 0;

                batch.draw(backs,0,0);
                glyphLayout.setText(font_score_real_time, "Score: " + score_real_time);
                font_score_real_time.draw(batch, glyphLayout, WIDTH / 2 - glyphLayout.width / 2, HEIGHT - 80);

            /*for (int i=0; i<arrayKosong.size(); i++) {
                batch.draw(backgrid, arrayGrid.get(Math.round(arrayKosong.get(i))-1).x, arrayGrid.get(Math.round(arrayKosong.get(i))-1).y);
            }*/
                for (int i=0; i<9; i++) {
                    batch.draw(backgrid, arrayGrid.get(i).x, arrayGrid.get(i).y);
                }

                /*for(int i=0; i<arrayGrid.size; i++) {
                    if (i == 2 || i == 5 || i == 8) {
                        batch.draw(backgrid, arrayGrid.get(i).x, arrayGrid.get(i).y);
                    }
                }*/

                //System.out.println();
                for (int i=0; i<arrayPosisi2.size(); i++) {
                    int z = Math.round(arrayPosisi2.get(i));
                    //System.out.print(", " + arrayPosisi2.size() + ", " + arrayJenis.size());
                    if((z == 3 || z == 6 || z == 9)) {
                        for(Kelinci k: arrayKelinci) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                batch.draw(backgrid, arrayGrid.get(z-2).x, arrayGrid.get(z-2).y);
                                break;
                            }
                        }
                        for(Tikus k: arrayTikus) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                batch.draw(backgrid, arrayGrid.get(z-2).x, arrayGrid.get(z-2).y);
                                break;
                            }
                        }
                    }
                    else if ((z == 2 || z == 5 || z == 8)) {
                        for(Kelinci k: arrayKelinci) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                batch.draw(backgrid, arrayGrid.get(z-2).x, arrayGrid.get(z-2).y);
                                break;
                            }
                        }
                        for(Tikus k: arrayTikus) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                batch.draw(backgrid, arrayGrid.get(z-2).x, arrayGrid.get(z-2).y);
                                break;
                            }
                        }
                    }
                    else if ((z == 1 || z == 4 || z == 7)) {
                        for(Kelinci k: arrayKelinci) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                break;
                            }
                        }
                        for(Tikus k: arrayTikus) {
                            if (k.index == z) {
                                k.drawTexture(batch, delta);
                                break;
                            }
                        }
                    }
                }
                //System.out.println();

                /*shape.setProjectionMatrix(camera.projection);
                shape.setTransformMatrix(camera.view);
                shape.begin(ShapeRenderer.ShapeType.Line);

            shape.rect(grid.x,grid.y,grid.width,grid.height);
            for (int i=0;i<9;i++){
                Rectangle r = arrayGrid.get(i);
                shape.rect(r.x, r.y, r.width, r.height);
            }

            for(int i=0; i<arrayGrid.size; i++) {
                if (i == 2 || i == 5 || i == 8) {
                    Rectangle r = arrayGrid.get(i);
                    shape.rect(r.x, r.y, r.width, r.height);
                }
            }

            //draw shape
            cArrKelinci = 0;
            cArrTikus = 0;
            for (int i=0; i<arrayPosisi2.size(); i++) {
                int z = Math.round(arrayPosisi2.get(i));
                if((z == 3 || z == 6 || z == 9)) {
                    if (arrayJenis.get(i) == 0.0) {
                        arrayTikus.get(cArrTikus).drawBody(shape);
                        cArrTikus++;
                    }
                    else {
                        arrayKelinci.get(cArrKelinci).drawBody(shape);
                        cArrKelinci++;
                    }
                }
                else if ((z == 2 || z == 5 || z == 8)) {
                    if (arrayJenis.get(i) == 0.0) {
                        arrayTikus.get(cArrTikus).drawBody(shape);
                        cArrTikus++;
                    }
                    else {
                        arrayKelinci.get(cArrKelinci).drawBody(shape);
                        cArrKelinci++;
                    }
                }
                else if ((z == 1 || z == 4 || z == 7)) {
                    if (arrayJenis.get(i) == 0.0) {
                        arrayTikus.get(cArrTikus).drawBody(shape);
                        cArrTikus++;
                    }
                    else {
                        arrayKelinci.get(cArrKelinci).drawBody(shape);
                        cArrKelinci++;
                    }
                }
            }

                shape.end();*/
                batch.draw(backgrid2, WIDTH/2-300f/2,90);
                batch.draw(backbottom, 0, 0);
                update(delta);
                batch.end();
            }

    }

    private void update(float delta) {
        //System.out.println(camera.viewportHeight);
        if (!gameOver) {
            elapsedTahan += delta;
            for (int i=0;i<arrayKelinci.size();i++){
                Circle badanKelinci = arrayKelinci.get(i).getBadanKelinci();
                Rectangle grid = arrayKelinci.get(i).getRect();

                if (badanKelinci.y < grid.y + 40 && arrayKelinci.get(i).muncul == true) {
                    if (elapsedTahan > arrayKelinci.get(i).tahan) {
                        arrayKelinci.get(i).muncul();
                    }
                }
                else {
                    arrayKelinci.get(i).muncul = false;
                    arrayKelinci.get(i).elapsedSembunyi += delta;

                    if (arrayKelinci.get(i).durasi == 0.0)
                        arrayKelinci.get(i).durasi = MathUtils.random(9 / difficulty) + 1;
                    if(arrayKelinci.get(i).elapsedSembunyi < arrayKelinci.get(i).durasi && arrayKelinci.get(i).elapsedSembunyi != 0) {
                        arrayKelinci.get(i).setAnimation(true);
                    }
                    if (arrayKelinci.get(i).elapsedSembunyi > arrayKelinci.get(i).durasi && badanKelinci.y > grid.y - 40) {
                        arrayKelinci.get(i).sembunyi();
                        arrayKelinci.get(i).setAnimation(false);
                    }
                    else if(badanKelinci.y <= grid.y - 40) {
                        arrayPosisi2.remove((float) arrayKelinci.get(i).index);
                        arrayKelinci.remove(i);
                    }
                }
            }

            for (int i=0;i<arrayTikus.size();i++){
                Circle badanTikus = arrayTikus.get(i).getBadanTikus();
                Rectangle grid = arrayTikus.get(i).getRect();

                if (badanTikus.y < grid.y + 40 && arrayTikus.get(i).muncul == true) {
                    if (elapsedTahan > arrayTikus.get(i).tahan) {
                        arrayTikus.get(i).muncul();
                    }
                }
                else {
                    arrayTikus.get(i).muncul = false;
                    arrayTikus.get(i).elapsedSembunyi += delta;

                    if (arrayTikus.get(i).durasi == 0.0)
                        arrayTikus.get(i).durasi = MathUtils.random(9 / difficulty) + 1;

                    if(arrayTikus.get(i).elapsedSembunyi < arrayTikus.get(i).durasi && arrayTikus.get(i).elapsedSembunyi != 0) {
                        arrayTikus.get(i).setAnimation(true);
                    }
                    if (arrayTikus.get(i).elapsedSembunyi > arrayTikus.get(i).durasi && badanTikus.y > grid.y - 40) {
                        arrayTikus.get(i).sembunyi();
                        arrayTikus.get(i).setAnimation(false);
                    } else if (badanTikus.y <= grid.y - 40) {
                        gameOver = true;
                        writePref(score_real_time);
                        arrayPosisi2.remove((float) arrayTikus.get(i).index);
                        arrayTikus.remove(i);
                    }
                }
            }

            for (int i=0;i<arrayTikus.size();i++){
                if(Gdx.input.isTouched())
                {
                    Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                    camera.unproject(tmp);
                    Circle textureBounds= arrayTikus.get(i).getBadanTikus();

                    if(textureBounds.contains(tmp.x,tmp.y))
                    {
                        sound_tikus.play();
                        score_real_time += 10;
                        arrayPosisi2.remove((float) arrayTikus.get(i).index);
                        arrayTikus.remove(i);
                    }
                }
            }

            for (int i=0;i<arrayKelinci.size();i++){
                if(Gdx.input.isTouched())
                {
                    Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
                    camera.unproject(tmp);
                    Circle textureBounds= arrayKelinci.get(i).getBadanKelinci();
                    // texture x is the x position of the texture
                    // texture y is the y position of the texture
                    // texturewidth is the width of the texture (you can get it with texture.getWidth() or textureRegion.getRegionWidth() if you have a texture region
                    // textureheight is the height of the texture (you can get it with texture.getHeight() or textureRegion.getRegionhHeight() if you have a texture region
                    if(textureBounds.contains(tmp.x,tmp.y))
                    {
                        sound_kelinci.play();
                        gameOver = true;
                        writePref(score_real_time);
                    }
                }
            }

            if (arrayPosisi2.size() == 0) {
                elapsedRonde += delta;
                if (elapsedRonde > 1.0)
                    rondeBaru();
            }
        }
        else {
            buatButtonGameOver();

            for (Kelinci k: arrayKelinci) {
                k.setAnimation(false);
            }
            for (Tikus t: arrayTikus) {
                t.setAnimation(false);
            }

            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);
            Rectangle btnGameOver= new Rectangle(buttonGameOver.getX(), buttonGameOver.getY(), buttonGameOver.getWidth(), buttonGameOver.getHeight());

            if(Gdx.input.isTouched()) {
                if (btnGameOver.contains(tmp.x, tmp.y)) {
                    sound_button.play(0.8f);
                    mode = 0;
                    reset();
                }
            }
        }
    }

    private void buatButtonPlay() {
        buttonPlay = new ImageButton(playRegionDrawable);
        buttonPlay.setPosition(150, 240);
        buttonPlay.setSize(180f,80f);
        buttonPlay.draw(batch, 1);
    }

    private void buatButtonKembali() {
        buttonKembali = new ImageButton(kembaliRegionDrawable);
        buttonKembali.setPosition(30, HEIGHT - 100);
        buttonKembali.setSize(80f,80f);
        //stage.addActor(buttonKembali);
    }

    private void buatButtonScore() {
        buttonScore = new ImageButton(scoreRegionDrawable);
        buttonScore.setPosition(150, 140);
        buttonScore.setSize(180f,80f);
        buttonScore.draw(batch, 1);
    }

    private void buatButtonQuit() {
        buttonQuit = new ImageButton(quitRegionDrawable);
        buttonQuit.setPosition(150, 40);
        buttonQuit.setSize(180f,80f);
        buttonQuit.draw(batch, 1);
    }

    private void buatButtonGameOver() {
        buttonGameOver = new ImageButton(gameOverRegionDrawable);
        buttonGameOver.setSize(180f,180f);
        buttonGameOver.setPosition(150, HEIGHT / 4 * 3 - buttonGameOver.getHeight() / 2);
        buttonGameOver.draw(batch, 1);
        glyphLayout.setText(font_game_over, gameOverText);
        font_game_over.draw(batch, glyphLayout, WIDTH / 2 - glyphLayout.width / 2, HEIGHT - 30);
    }

    public void writePref(int new_score) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        temp.add(pref.getInteger("1"));
        temp.add(pref.getInteger("2"));
        temp.add(pref.getInteger("3"));
        temp.add(pref.getInteger("4"));
        temp.add(pref.getInteger("5"));

        for (int i=0; i<5; i++) {
            if (temp.get(i) < new_score) {
                temp.add(i, new_score);
                break;
            }
        }

        pref.putInteger("1", temp.get(0));
        pref.putInteger("2", temp.get(1));
        pref.putInteger("3", temp.get(2));
        pref.putInteger("4", temp.get(3));
        pref.putInteger("5", temp.get(4));

        pref.flush();
    }

    public ArrayList<Integer> readPref() {
        ArrayList<Integer> score = new ArrayList<Integer>();
        score.add(pref.getInteger("1"));
        score.add(pref.getInteger("2"));
        score.add(pref.getInteger("3"));
        score.add(pref.getInteger("4"));
        score.add(pref.getInteger("5"));

        return score;
    }

    public void reset() {
        arrayGrid.clear();
        arrayPosisi.clear();
        arrayPosisi2.clear();
        arrayKelinci.clear();
        arrayTikus.clear();
        score_real_time = 0;
        difficulty = 1;
    }
}
