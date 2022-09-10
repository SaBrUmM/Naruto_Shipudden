package com.badlogic.drop;

import com.badlogic.drop.actors.Background;
import com.badlogic.drop.actors.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {
    final Drop game;
    SpriteBatch batch;
    Texture img, narutologo, sailogo, sakuralogo;

    private Stage stage;
    private Level level1, level2, level3;
    private boolean inp1 = false, inp2 = false,inp3 = false;


    public MainMenuScreen(final Drop gam) {
        this.game = gam;
        batch = new SpriteBatch();
        img = new Texture("ap.png");
        narutologo = new Texture("narutologo.png");
        sailogo = new Texture("logomystery.png");
        sakuralogo = new Texture("logomystery.png");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        level1 = new Level(narutologo);
        level1.setBounds(Gdx.graphics.getWidth() * 0.8f, Gdx.graphics.getHeight() / 2, 150, 185);
        level2 = new Level(sakuralogo);
        level2.setBounds(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.25f, 150, 185);
        level3 = new Level(sailogo);
        level3.setBounds(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() * 0.45f, 150, 185);
        Background background = new Background(img);
        background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(background);
        stage.addActor(level1);
        stage.addActor(level2);
        stage.addActor(level3);

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        Gdx.gl20.glClearColor(0,0,0,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        batch.end();
        stage.act(delta);
        stage.draw();
        if (Gdx.input.isTouched()) {
            game.setScreen(game.gameScreen);
        }


    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, false);
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
        stage.dispose();
        batch.dispose();
        img.dispose();
        narutologo.dispose();
        sailogo.dispose();
        sakuralogo.dispose();
    }
}
