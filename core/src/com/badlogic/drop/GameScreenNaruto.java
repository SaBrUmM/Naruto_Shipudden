package com.badlogic.drop;


import com.badlogic.drop.actors.Controller;
import com.badlogic.drop.actors.Sprites.Gate;
import com.badlogic.drop.actors.Sprites.Naruto;
import com.badlogic.drop.actors.Sprites.Obtacle;
import com.badlogic.drop.actors.Sprites.Orochimaru;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreenNaruto implements Screen {
	private Drop game;
	private TmxMapLoader tmxMapLoader;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer renderer;
	private World world;
	private Box2DDebugRenderer box2DDebugRenderer;
	private Naruto player;
	private Orochimaru orochimaru1;
	private Orochimaru orochimaru2;
	private Orochimaru orochimaru3;
	private Orochimaru orochimaru4;
	private Obtacle obtacle;
	private Stage stage;
	private SpriteBatch batch;
	Controller right;
	Controller left;
	Controller up;
	private TextureAtlas atlas;


	private Music music;


	public GameScreenNaruto(Drop drop) {
		atlas = new TextureAtlas("Naruto_New_Package.txt");
		this.game = drop;
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		tmxMapLoader = new TmxMapLoader();
		tiledMap = tmxMapLoader.load("mapnaruto1.tmx");
		renderer = new OrthogonalTiledMapRenderer(tiledMap,1/game.PPM);
		game.camera.position.set(game.viewport.getWorldWidth()/2, game.viewport.getWorldHeight()/2,0);


		world = new World(new Vector2(0,-10), true);
		//box2DDebugRenderer = new Box2DDebugRenderer();


		BodyDef bodyDef = new BodyDef();
		PolygonShape pShape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		Body body;


		player = new Naruto(world,this);
		Texture imgright = new Texture("play.png");
		Texture imgleft = new Texture("play2.png");
		right = new Controller(imgright);
		right.setBounds(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.09f,Gdx.graphics.getWidth()*0.117f,Gdx.graphics.getHeight()*0.21f);
		left = new Controller(imgleft);
		left.setBounds(right.getX() - right.getWidth()*1.2f,Gdx.graphics.getHeight()*0.09f,Gdx.graphics.getWidth()*0.117f,Gdx.graphics.getHeight()*0.21f);
		up = new Controller(new Texture("up.png"));
		up.setBounds(Gdx.graphics.getWidth()*0.8f,Gdx.graphics.getHeight()*0.1f,Gdx.graphics.getWidth()*0.117f,Gdx.graphics.getHeight()*0.21f);
		stage.addActor(right);
		stage.addActor(left);
		stage.addActor(up);


		world.setContactListener(new WorldContactListener());

		music = Drop.manager.get("Level_1.ogg", Music.class);
		music.setLooping(true);
		music.play();


		orochimaru1 = new Orochimaru(world, this, 8.64f, .64f);
		orochimaru2 = new Orochimaru(world, this, 15.64f, .64f);
		orochimaru3 = new Orochimaru(world, this, 25.64f, .64f);
		orochimaru4 = new Orochimaru(world, this, 30.64f, .64f);




		//Переносит объекты из TiledMap в игру
		for(MapObject object: tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set((rect.getX() + rect.getWidth()/2)/game.PPM, (rect.getY() + rect.getHeight()/2)/game.PPM);

			body = world.createBody(bodyDef);

			pShape.setAsBox(rect.getWidth()/2/game.PPM,rect.getHeight()/2/game.PPM);
			fixtureDef.shape = pShape;
			body.createFixture(fixtureDef);
		}
		for(MapObject object: tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / game.PPM, (rect.getY() + rect.getHeight() / 2) / game.PPM);

			body = world.createBody(bodyDef);

			pShape.setAsBox(rect.getWidth() / 2 / game.PPM, rect.getHeight() / 2 / game.PPM);
			fixtureDef.shape = pShape;
			body.createFixture(fixtureDef);
		}
		for(MapObject object: tiledMap.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set((rect.getX() + rect.getWidth()/2)/game.PPM, (rect.getY() + rect.getHeight()/2)/game.PPM);

			body = world.createBody(bodyDef);

			pShape.setAsBox(rect.getWidth()/2/game.PPM,rect.getHeight()/2/game.PPM);
			fixtureDef.shape = pShape;
			body.createFixture(fixtureDef);
		}
		for(MapObject object: tiledMap.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			new Gate(world, tiledMap, rect);

		}
		for(MapObject object: tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			new Obtacle(world, tiledMap, rect);



		}
		for(MapObject object: tiledMap.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();

			bodyDef.type = BodyDef.BodyType.StaticBody;
			bodyDef.position.set((rect.getX() + rect.getWidth()/2)/game.PPM, (rect.getY() + rect.getHeight()/2)/game.PPM);

			body = world.createBody(bodyDef);

			pShape.setAsBox(rect.getWidth()/2/game.PPM,rect.getHeight()/2/game.PPM);
			fixtureDef.shape = pShape;
			body.createFixture(fixtureDef);
		}



	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {

	}
	public void handleInput(float delta) {
		if (player.cur != Naruto.State.DEAD && player.cur != Naruto.State.WIN) {
			if (right.isPressed() && player.b2body.getLinearVelocity().x <= 2)
				player.b2body.applyLinearImpulse(new Vector2(2, 0), player.b2body.getWorldCenter(), true);
			if (left.isPressed() && player.b2body.getLinearVelocity().x >= -2)
				player.b2body.applyLinearImpulse(new Vector2(-2, 0), player.b2body.getWorldCenter(), true);
			if (up.isPressed() && (player.b2body.getPosition().y < 0.9f)) {
				player.b2body.applyLinearImpulse(new Vector2(0, 1f), player.b2body.getWorldCenter(), true);
			}
			if (up.isPressed() && (player.b2body.getLinearVelocity().y == 0)) {
				player.b2body.applyLinearImpulse(new Vector2(0, 6f), player.b2body.getWorldCenter(), true);
			}


			if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && (player.b2body.getLinearVelocity().y == 0))
				player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
				player.b2body.applyLinearImpulse(new Vector2(5, 0), player.b2body.getWorldCenter(), true);
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
				player.b2body.applyLinearImpulse(new Vector2(-5, 0), player.b2body.getWorldCenter(), true);
		}
	}
	public void update(float delta){
		handleInput(delta);
		player.update(delta);
		orochimaru1.update(delta);
		orochimaru2.update(delta);
		orochimaru3.update(delta);
		orochimaru4.update(delta);
		world.step(1/60f,6,2);
		game.camera.update();
		renderer.setView(game.camera);

		if(player.cur != Naruto.State.DEAD) {
			game.camera.position.x = player.b2body.getPosition().x;
		}

	}

	@Override
	public void render(float delta) {
		update(delta);
		game.batch.setProjectionMatrix(game.camera.combined);
		batch.begin();
		Gdx.gl20.glClearColor(0,0,0,1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		//box2DDebugRenderer.render(world,game.camera.combined);
		batch.end();
		game.batch.begin();
		player.draw(game.batch);
		orochimaru1.draw(game.batch);
		orochimaru2.draw(game.batch);
		orochimaru3.draw(game.batch);
		orochimaru4.draw(game.batch);
		game.batch.setProjectionMatrix(stage.getCamera().combined);
		game.batch.end();
		stage.act(delta);
		stage.draw();

		if(gameOver()){
			game.setScreen(new GameOverScreen(game));
		}
		if(gameWin()){
			game.setScreen(new YouWinScreen(game));
		}

	}

	public boolean gameOver(){
		if(player.cur == Naruto.State.DEAD && player.getStateTimer() > 3) {
			return true;
		}
		return false;
	}

	public boolean gameWin(){
		if(player.cur == Naruto.State.WIN && player.getStateTimer() > 3) {
			return true;
		}
		return false;
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),true);
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
	public void dispose () {
		game.batch.dispose();
		renderer.dispose();
		//box2DDebugRenderer.dispose();
		world.dispose();
		batch.dispose();

	}

	public TiledMap getMap() {
		return tiledMap;
	}

	public World getWorld() {
		return world;
	}
}
