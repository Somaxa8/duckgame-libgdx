package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.*

class Floor(private val world: World) {
    val width = 600f
    val height = 50f

    var body: Body
//    var texture: Texture

    val x: Float = (Gdx.graphics.width / 2).toFloat()
    val y: Float = -80f

    init {
//        texture = Texture("floor.png")
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position.set(x, y)
        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(width / 2, height)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        val fixture = body.createFixture(fixtureDef)
        fixture.userData = "floor"
        shape.dispose()
    }

    fun render(batch: SpriteBatch) {
        world.step(Gdx.graphics.deltaTime, 6, 2)
//        batch.draw(texture, x, y, width, height)
    }
}