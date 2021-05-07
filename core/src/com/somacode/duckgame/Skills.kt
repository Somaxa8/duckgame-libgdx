package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.somacode.duckgame.util.Updatable

class Skills(val body: Body): Updatable {

    var jumpAnimation: Animation<TextureRegion>

    init {
        val texture = Texture("duck.png")
        val textureRegion = TextureRegion.split(texture, 25, 25)
        jumpAnimation = Animation(1f, *textureRegion[2])
    }

    fun jump() {
        val position = body.position
        body.applyLinearImpulse(0f, 5000f, position.x, position.y, true)
    }

    override fun update(float: Float) {
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            jump()
        }
    }
}