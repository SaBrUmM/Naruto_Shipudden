package com.badlogic.drop.actors.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.GameScreenNaruto;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Naruto extends Sprite {


    public enum State {JUMPING,RUNNING,STANDING,FALLING, DEAD, WIN}
    public State cur;
    public State prev;
    public World world;
    public Body b2body;
    private TextureRegion NarutoRegion;
    private Animation<TextureRegion> narutoStand;
    private Animation<TextureRegion> narutoRun;
    private Animation<TextureRegion> narutoJump;
    private Animation<TextureRegion> narutoDead;
    private Animation<TextureRegion> narutoWin;
    private float stateTimer;
    private boolean runningRight;
    private boolean narutoisdead;
    private boolean narutoiswin;


    public Naruto(World world, GameScreenNaruto screen) {
        super(screen.getAtlas().findRegion("naruto"));
        cur = State.STANDING;
        prev = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,0,80,80));
        }
        narutoStand = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 4; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,0,80,80));
        }
        narutoRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 10; i < 14; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,0,80,80));
        }

        narutoJump = new Animation<TextureRegion>(0.15f,frames);
        frames.clear();
        for (int i = 14; i < 17; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,0,80,80));
        }
        narutoDead = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for (int i = 18; i < 20; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,0,80,80));
        }
        narutoWin = new Animation<TextureRegion>(0.5f, frames);
        frames.clear();


        this.world = world;
        defineNaruto();
        NarutoRegion = new TextureRegion(getTexture(),0,80,70,80);
        setBounds(0,0,1,1);
        setRegion(NarutoRegion);
    }
    public void setVelocity(float x, float y){
        b2body.setLinearVelocity(x,y);
    }


    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta){
        cur = getState();
        TextureRegion region;
        switch (cur){
            case DEAD:
                region = narutoDead.getKeyFrame(stateTimer);
                break;
            case WIN:
                region = narutoWin.getKeyFrame(stateTimer);
                break;
            case JUMPING:
                region = narutoJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = narutoRun.getKeyFrame(stateTimer,true);
                break;
            default:
                region = narutoStand.getKeyFrame(stateTimer,true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }

        stateTimer = cur ==  prev ? stateTimer + delta : 0;
        prev = cur;
        return region;
    }

    public State getState(){
        if (narutoisdead)
            return State.DEAD;
        else if (narutoiswin)
            return State.WIN;
        else if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && prev == State.JUMPING) ){
            return State.JUMPING;
        }
        else if (b2body.getLinearVelocity().y < 0){
            return State.FALLING;
        }
        else if(b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else return State.STANDING;
    }

    public boolean isDead(){
        return narutoisdead;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public void hit(){
        Drop.manager.get("Level_1.ogg", Music.class).stop();
        Drop.manager.get("Sounds/Oof.wav", Sound.class).play();
        narutoisdead = true;
        Filter filter = new Filter();
        filter.maskBits = Drop.NO_COLLISION_BIT;
        for(Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(-2f, 6f), b2body.getWorldCenter(), true);
    }

    public void win()   {
        Drop.manager.get("Level_1.ogg", Music.class).stop();
        Drop.manager.get("Sounds/Win.wav", Sound.class).play();
        narutoiswin = true;
    }

    public void defineNaruto(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(500/100,128/100);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(42 / Drop.PPM);
        fixtureDef.filter.categoryBits = Drop.NARUTO_BIT;
        fixtureDef.filter.maskBits = Drop.GROUND_BIT |
                Drop.ENEMY_BIT |
                Drop.OBTACLE_BIT |
                Drop.OBJECT_BIT |
                Drop.GATE_BIT;
        fixtureDef.shape = circleShape;
        fixtureDef.friction=25;





        b2body.createFixture(fixtureDef).setUserData(this);
    }
}
