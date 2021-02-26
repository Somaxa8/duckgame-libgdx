package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.*

class Floor(private val world: World) {
    val WIDTH = 400f
    val HEIGHT = 50f

    var body: Body? = null
    var texture: Texture? = null

    val x: Float = 10f
    val y: Float = -70f

    init {
        texture = Texture("floor.png")
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position[x] = y
        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(WIDTH / 2, 0.5f)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        val fixture = body!!.createFixture(fixtureDef)
        fixture.userData = "floor"
        shape.dispose()
    }

    fun render(batch: SpriteBatch) {
        world.step(Gdx.graphics.deltaTime, 6, 2)
        batch.draw(texture, x, y, WIDTH, HEIGHT)
    }
}