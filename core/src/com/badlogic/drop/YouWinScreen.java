package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class YouWinScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public YouWinScreen(Game game){
        this.game = game;
        viewport = new FitViewport(200, 200, new OrthographicCamera(10, 10));
        stage = new Stage(viewport, ((Drop) game).batch);

        Label.LabelStyle fontwin = new Label.LabelStyle(new BitmapFont(), Color.GREEN);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameoverlabel = new Label("YOU WIN!", fontwin);
        Label playagainlabel = new Label("Tap to play again.", font);


        table.add(gameoverlabel).expandX();
        table.row();
        table.add(playagainlabel).expandX().padTop(30f);

        stage.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched())
            game.setScreen(new GameScreenNaruto((Drop) game));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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
}
