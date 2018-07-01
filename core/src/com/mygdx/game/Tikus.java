package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by EdgarPriyaD on 5/29/2018.
 */

public class Tikus {
    private float x = 0;
    private float y = 0;

    private float ySpeed = 0;
    private static float PERCEPATAN = 0.1F;
    private static float KECEPATAN_TERBANG = 3F;

    private static final float RADIUS = 40f;
    private Circle body;
    private final Texture gambarTikus;

    private Rectangle rect;

    public boolean muncul = true;
    public float durasi = 0;
    public int index = -1;
    public float elapsedSembunyi = 0;

    public float tahan = 0;

    public int diff = 0;

    private Animation animation;
    private TextureAtlas textureAtlas;
    private float elapsedAnim = 0;
    private TextureRegion current;

    //Constructor
    public Tikus(Texture t, Rectangle rect, int index) {
        this.rect = rect;
        this.x = (rect.getX() + rect.getWidth() / 2);
        this.y = (rect.getY() + rect.getHeight() / 2)-90;

        body = new Circle(x, y, RADIUS);
        this.gambarTikus = t;

        this.index = index;
    }

    public Tikus(Texture t) {

        body = new Circle(x, y, RADIUS);
        this.gambarTikus = t;

        textureAtlas = new TextureAtlas(Gdx.files.internal("tikus.atlas"));
        Array<TextureAtlas.AtlasRegion> tikusFrame = textureAtlas.findRegions("tikus");
        animation = new Animation(0, tikusFrame, Animation.PlayMode.LOOP);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(boolean mulai) {
        if (mulai) {
            animation.setFrameDuration(1/2f);
            animation.setPlayMode(Animation.PlayMode.LOOP);
        }
        else {
            animation.setFrameDuration(0);
        }
    }

    public void setDiff(int diff) {
        this.diff = diff;
        if (this.diff == 1) {
            KECEPATAN_TERBANG = 2F;
        }
        else if (this.diff == 2) {
            KECEPATAN_TERBANG = 3F;
        }
        else if (this.diff == 3) {
            KECEPATAN_TERBANG = 4F;
        }
        this.tahan = MathUtils.random(9 / diff);
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
        this.x = (rect.getX() + rect.getWidth() / 2);
        this.y = (rect.getY() + rect.getHeight() / 2)-90;
    }

    public void drawTexture(Batch batch, float delta){
        if (animation.getFrameDuration() != 0) {
            elapsedAnim += delta;
        }
        float xT, yT;
        xT = body.x - gambarTikus.getWidth()/2;
        yT = body.y - gambarTikus.getHeight()/2;
        current = (TextureRegion) animation.getKeyFrame(elapsedAnim);
        batch.draw(current, xT, yT);
    }

    /*public void drawTexture(Batch batch, TextureRegion region){
        float xT, yT;
        xT = body.x - gambarTikus.getWidth()/2;
        yT = body.y - gambarTikus.getHeight()/2;
        batch.draw(region, xT, yT);
    }*/

    public void drawBody(ShapeRenderer shape) {
        shape.circle(body.x, body.y, body.radius);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBodyPosition(x, y);
    }

    public Circle getBadanTikus() {
        return body;
    }

    private void updateBodyPosition(float x, float y) {
        body.setX(x);
        body.setY(y);
    }

    public void update() {
        ySpeed = ySpeed - PERCEPATAN;
        setPosition(x, y + ySpeed);
    }

    public void muncul() {
        ySpeed = KECEPATAN_TERBANG;
        setPosition(x, y + ySpeed);
    }

    public void sembunyi() {
        ySpeed = -KECEPATAN_TERBANG;
        setPosition(x, y + ySpeed);
    }

    public Rectangle getRect() {
        return rect;
    }

    public float getY() {
        return y;
    }

}
