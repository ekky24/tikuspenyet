package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by EdgarPriyaD on 5/29/2018.
 */

public class Kelinci {
    private float x = 0;
    private float y = 0;

    private float ySpeed = 0;
    private static float PERCEPATAN = 0.3F;
    private static float KECEPATAN_TERBANG = 3F;

    private static final float RADIUS = 24f;
    private Circle body;
    private final Texture gambarKelinci;

    //Constructor
    public Kelinci(Texture t, Rectangle rect) {
        /*
        if (posisi <= 3) {
            this.x = posisi*80;
            this.y = 0;
        } else if (posisi <= 6) {
            posisi = posisi - 3;
            this.x = posisi*80;
            this.y = 270;
        } else if (posisi <= 9) {
            posisi = posisi - 6;
            this.x = posisi*80;
            this.y = 450;
        }*/
        this.x = (rect.getX() + rect.getWidth() / 2);
        this.y = rect.getY() + rect.getHeight() / 2;

        body = new Circle(x, y, RADIUS);
        this.gambarKelinci = t;
    }

    public void drawTexture(Batch batch){
        float xT, yT;
        xT = body.x - gambarKelinci.getWidth()/2;
        yT = body.y - gambarKelinci.getHeight()/2;
        batch.draw(gambarKelinci, xT, yT);
    }
}
