package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    //public BitmapFont gameFont;
    //private SpriteBatch batch;
    private Game game;
    //private OrthographicCamera camera;

    public GameOverScreen(Game game){
        this.game = game;
        viewport = new FitViewport(500, 300, new OrthographicCamera(10, 10));
        stage = new Stage(viewport, ((Drop) game).batch);

        /*gameFont = new BitmapFont(Gdx.files.internal("Game_Naruto_Font.fnt"), Gdx.files.internal("Game_Naruto_Font.png"), false);
        gameFont.setColor(Color.RED);
        gameFont.getData().setScale( 5f, 5f);

        camera = new OrthographicCamera(1000, 1000);

        batch = new SpriteBatch();*/

        Label.LabelStyle fontgameover = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameoverlabel = new Label("GAME OVER!", fontgameover);
        Label playAgainlabel = new Label("Tap to play again", font);
        Label hintlabel = new Label("Tip: purple bushes are poisonous and kill you in a moment. Beware them!", font);


        table.add(gameoverlabel).expandX();
        table.row();
        table.add(playAgainlabel).expandX().padTop(60f);
        table.row();
        table.add(hintlabel).expandX().padTop(60f);

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

        /*batch.setProjectionMatrix(camera.combined);
        batch.begin();
        gameFont.draw(batch, "GAME OVER!", -100, 100);
        batch.end();*/
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
