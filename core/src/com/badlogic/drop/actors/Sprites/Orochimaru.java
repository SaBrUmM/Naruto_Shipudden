package com.badlogic.drop.actors.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.drop.GameScreenNaruto;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Orochimaru extends Sprite {

    public enum State {RUNNING,STANDING}
    private float stateTime;
    private Array<TextureRegion> frames;
    private TextureRegion OrochimaruRegion;
    public Orochimaru.State cur;
    public Orochimaru.State prev;
    private boolean runningRight;
    private Animation<TextureRegion> orochimaruStand;
    private Animation<TextureRegion> orochimaruRun;
    protected World world;
    protected GameScreenNaruto screen;
    public Body b2body;
    public Vector2 velocity;

    public Orochimaru(World world, GameScreenNaruto screen, float x, float y) {

        super(screen.getAtlas().findRegion("Orochimaru"));
        this.world = world;
        this.screen = screen;
        setPosition(x, y);
        velocity = new Vector2(3, 0);
        cur = Orochimaru.State.STANDING;
        prev = Orochimaru.State.STANDING;
        stateTime = 0;
        runningRight = true;
        frames = new Array<TextureRegion>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 6; i < 10; i++) {
            frames.add(new TextureRegion(getTexture(),i*80,80,80,80));
        }
        orochimaruStand = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();
        for (int i = 0; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 80 , 80, 80, 80));
        }
        orochimaruRun = new Animation<TextureRegion>(0.1f,frames);
        frames.clear();





        stateTime = 0;
        this.world = world;
        defineOrochimaru();
        OrochimaruRegion = new TextureRegion(getTexture(),0,0,80,80);
        setBounds(0,0,1,1);
        setRegion(OrochimaruRegion);

    }

    public void update(float dt) {
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));

    }


    public void defineOrochimaru() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(40 / Drop.PPM);
        fixtureDef.filter.categoryBits = Drop.ENEMY_BIT;
        fixtureDef.filter.maskBits = Drop.GROUND_BIT | Drop.OBTACLE_BIT | Drop.ENEMY_BIT | Drop.NARUTO_BIT;
        fixtureDef.shape = circleShape;
        fixtureDef.friction = 25;


        b2body.createFixture(fixtureDef).setUserData(this);
    }

    public TextureRegion getFrame(float delta) {
        cur = getState();
        TextureRegion region;
        switch (cur) {
            case RUNNING:
                region = orochimaruRun.getKeyFrame(stateTime, true);
                break;
            default:
                region = orochimaruStand.getKeyFrame(stateTime, true);
                break;
        }
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTime = cur == prev ? stateTime + delta : 0;
        prev = cur;
        return region;
    }

    public Orochimaru.State getState() {

        if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else
            return State.STANDING;

    }

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;

        if (y)
            velocity.y = -velocity.y;

    }


}
