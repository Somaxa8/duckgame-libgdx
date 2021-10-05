package com.somacode.duckgame.tool

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.somacode.duckgame.Entity

class WorldContactListener(val entity: Entity) : ContactListener {

    override fun beginContact(contact: Contact?) {
        entity.isJumping = false
        Gdx.app.log("Begin Contact", "")
    }

    override fun endContact(contact: Contact?) {
        entity.status = Entity.Status.JUMPING
        entity.isJumping = true
        Gdx.app.log("End Contact", "")
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {
    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {
    }
}