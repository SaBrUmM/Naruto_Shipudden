package com.badlogic.drop;

import static java.awt.image.ImageObserver.HEIGHT;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Drop extends Game {
    SpriteBatch batch;
    BitmapFont font;
    MainMenuScreen menu;
    GameScreenNaruto gameScreen;
    OrthographicCamera camera;
    Viewport viewport;
    public static final float  WIDTH = 720;
    public static final float HEIGHT = 480;
    public static final float PPM = 100;

    public static final short NO_COLLISION_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short NARUTO_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short OBTACLE_BIT = 8;
    public static final short ENEMY_BIT = 32;
    public static final short GATE_BIT = 64;




    public static AssetManager manager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        manager = new AssetManager();
        manager.load("Level_1.ogg", Music.class);
        manager.load("Sounds/Oof.wav", Sound.class);
        manager.load("Sounds/Win.wav", Sound.class);
        manager.finishLoading();
        camera =  new OrthographicCamera();
        camera.setToOrtho(false,WIDTH/PPM,HEIGHT/PPM);
        viewport = new ExtendViewport(WIDTH / PPM,HEIGHT/ PPM,camera);
        menu = new MainMenuScreen(this);
        gameScreen = new GameScreenNaruto(this);
        setScreen(menu);
    }

    @Override
    public void render() {
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();
    }
}
