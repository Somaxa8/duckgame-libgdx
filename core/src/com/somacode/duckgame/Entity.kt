package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*


class Entity(private val world: World) {
    val SPEED = 3f
    val SIZE = 25f
    val WIDTH = 20f
    val HEIGHT = 25f

    enum class Direction { UP, DOWN, LEFT, RIGHT }

    var walkAnimation: Animation<TextureRegion>
    var stateTime: Float = 0f
    var direction: Direction = Direction.LEFT
    var x: Float = Gdx.graphics.width / 2f - SIZE / 2f
    var y: Float = 0f
    var textureRegion: TextureRegion? = null
    var isIdle: Boolean = true
    var idleAnimation: Animation<TextureRegion>
    var body: Body? = null

    init {
        // Animations
        val texture = Texture("duck1.png")
        val textureRegion = TextureRegion.split(texture, 20, 25)
        idleAnimation = Animation(0.1f, textureRegion[0][0])
        walkAnimation = Animation(0.1f, textureRegion[0][1], textureRegion[0][2], textureRegion[0][3], textureRegion[0][4], textureRegion[0][5])
        walkAnimation.playMode = Animation.PlayMode.LOOP
        idleAnimation.playMode = Animation.PlayMode.LOOP

        // Physics
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position[x] = y
        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(WIDTH / 2, HEIGHT / 2)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        val fixture = body!!.createFixture(fixtureDef)
        shape.dispose()

    }

    fun render(spriteBatch: SpriteBatch) {
        world.step(Gdx.graphics.deltaTime, 6, 2)

        isIdle = true
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            direction = Direction.LEFT
            isIdle = false
            x += SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            direction = Direction.RIGHT
            isIdle = false
            x -= SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            jump()
        }

        stateTime += Gdx.graphics.deltaTime
        val animation = if (isIdle) idleAnimation else walkAnimation
        when (direction) {
            Direction.LEFT -> {
                textureRegion = animation.getKeyFrame(stateTime)
                if (!textureRegion!!.isFlipX) {
                    textureRegion!!.flip(true, false)
                }

            }
            Direction.RIGHT -> {
                textureRegion = animation.getKeyFrame(stateTime)
                if (textureRegion!!.isFlipX) {
                    textureRegion!!.flip(true, false)
                }
            }
        }

        spriteBatch.draw(textureRegion, body!!.position.x, body!!.position.y, SIZE, SIZE)
    }

    fun jump() {
        val position = body!!.position
        body!!.applyLinearImpulse(0f, 20f, position.x, position.y, true)
    }

}