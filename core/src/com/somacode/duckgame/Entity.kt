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
    val width = 25f
    val height = 25f

    enum class Direction { UP, DOWN, LEFT, RIGHT, IDLE }

    var walkAnimation: Animation<TextureRegion>
    var idleAnimation: Animation<TextureRegion>
    var jumpAnimation: Animation<TextureRegion>
    var stateTime: Float = 0f
    var direction: Direction = Direction.LEFT
    var x: Float = Gdx.graphics.width / 2f - width / 2f
    var y: Float = 0f
    lateinit var textureRegion: TextureRegion
    var isIdle: Boolean = true
    var body: Body

    init {
        // Animations
        val texture = Texture("duck.png")
        val textureRegion = TextureRegion.split(texture, 25, 25)
        idleAnimation = Animation(0.1f, textureRegion[0][0])
        walkAnimation = Animation(0.1f, *textureRegion[1])
        jumpAnimation = Animation(1f, *textureRegion[2])

        walkAnimation.playMode = Animation.PlayMode.LOOP
        idleAnimation.playMode = Animation.PlayMode.LOOP

        // Physics
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.DynamicBody
        bodyDef.position.set(x, y)
        body = world.createBody(bodyDef)

        val shape = PolygonShape()
        shape.setAsBox(width / 2, height / 2)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1f

        val fixture = body.createFixture(fixtureDef)
        shape.dispose()

    }

    fun update(delta: Float) {
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
            direction = Direction.UP
            isIdle = false
            jump()
        }
        if (isIdle) direction = Direction.IDLE

        stateTime += Gdx.graphics.deltaTime
        when (direction) {
            Direction.IDLE -> {
                textureRegion = idleAnimation.getKeyFrame(stateTime)
            }
            Direction.LEFT -> {
                textureRegion = walkAnimation.getKeyFrame(stateTime)
                if (!textureRegion.isFlipX) {
                    println("hola")
                    textureRegion.flip(true, false)
                }

            }
            Direction.RIGHT -> {
                textureRegion = walkAnimation.getKeyFrame(stateTime)
                if (textureRegion.isFlipX) {
                    textureRegion.flip(true, false)
                }
            }
            Direction.UP -> {
                textureRegion = jumpAnimation.getKeyFrame(stateTime)
//                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//                    textureRegion.flip(true, false)
//                }
//                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//                    textureRegion.flip(true, false)
//                }
            }
        }
    }

    fun draw(spriteBatch: SpriteBatch) {
        spriteBatch.draw(textureRegion, body.position.x - width / 2, body.position.y - height / 2, width, height)
    }

    fun jump() {
        val position = body.position
        body.applyLinearImpulse(0f, 5000f, position.x, position.y, true)
    }

}