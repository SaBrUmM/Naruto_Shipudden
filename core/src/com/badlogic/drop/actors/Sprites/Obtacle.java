package com.badlogic.drop.actors.Sprites;

import com.badlogic.drop.Drop;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Obtacle extends InteractiveTileObject {

    protected Fixture fixture;

    public Obtacle(World world, TiledMap tiledMap, Rectangle bounds) {
        super(world, tiledMap, bounds);
        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape pShape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.getX() + bounds.getWidth() / 2) / Drop.PPM, (bounds.getY() + bounds.getHeight() / 2) / Drop.PPM);

        body = world.createBody(bodyDef);

        pShape.setAsBox(bounds.getWidth() / 2 / Drop.PPM, bounds.getHeight() / 2 / Drop.PPM);
        fixtureDef.shape = pShape;
        fixtureDef.filter.categoryBits = Drop.OBTACLE_BIT;
        fixture = body.createFixture(fixtureDef);

        fixture.setUserData(this);
    }

    @Override
    public void onBodyHit() {

    }
}

