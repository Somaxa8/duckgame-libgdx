package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.Body
import com.somacode.duckgame.util.Updatable

class Skills(private val body: Body, private val textureRegion: TextureRegion): Updatable {

    var jumpAnimation: Animation<TextureRegion>
    private var stateTime: Float = 0.0f

    init {
        val texture = Texture("duck.png")
        val aTextureRegion = TextureRegion.split(texture, 25, 25)
        jumpAnimation = Animation(1f, *aTextureRegion[2])
    }

    private fun jump() {
        val position = body.position
        body.applyLinearImpulse(0f, 5000f, position.x, position.y, true)
    }

    override fun update(float: Float) {
        stateTime += Gdx.graphics.deltaTime
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            textureRegion.setRegion(jumpAnimation.getKeyFrame(stateTime))
            jump()
        }
    }
}