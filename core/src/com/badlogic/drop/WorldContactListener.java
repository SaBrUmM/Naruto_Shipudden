package com.badlogic.drop;


import com.badlogic.drop.actors.Sprites.InteractiveTileObject;
import com.badlogic.drop.actors.Sprites.Naruto;
import com.badlogic.drop.actors.Sprites.Orochimaru;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        if (fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onBodyHit();
            }
        }

        switch(cDef){
            case Drop.ENEMY_BIT | Drop.OBTACLE_BIT:
                if(fixA.getFilterData().categoryBits == Drop.ENEMY_BIT)
                    ((Orochimaru)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Orochimaru)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Drop.ENEMY_BIT | Drop.NARUTO_BIT:
            case Drop.NARUTO_BIT | Drop.OBTACLE_BIT:
                if(fixA.getFilterData().categoryBits == Drop.NARUTO_BIT)
                    ((Naruto)fixA.getUserData()).hit();
                else
                    ((Naruto)fixB.getUserData()).hit();
                break;
            case Drop.ENEMY_BIT | Drop.ENEMY_BIT:
                ((Orochimaru)fixA.getUserData()).reverseVelocity(true, false);
                ((Orochimaru)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case Drop.GATE_BIT| Drop.NARUTO_BIT:
                if(fixA.getFilterData().categoryBits == Drop.NARUTO_BIT)
                    ((Naruto)fixA.getUserData()).win();
                else
                    ((Naruto)fixB.getUserData()).win();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
