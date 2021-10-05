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

    enum class Direction { DOWN, LEFT, RIGHT }
    enum class Status { IDlE, WALKING, JUMPING }
    var isJumping: Boolean = false
    var status: Status = Status.IDlE

    var body: Body
    var textureRegion: TextureRegion = TextureRegion()
    var walkAnimation: Animation<TextureRegion>
    var idleAnimation: Animation<TextureRegion>
    var jumpAnimation: Animation<TextureRegion>
    var x: Float = Gdx.graphics.width / 2f - width / 2f
    var y: Float = 0f
    var direction: Direction = Direction.LEFT
    var stateTime: Float = 0f

    init {
        // Animations
        val texture = Texture("duck.png")
        val aTextureRegion = TextureRegion.split(texture, 25, 25)
        idleAnimation = Animation(0.1f, aTextureRegion[0][0])
        walkAnimation = Animation(0.1f, *aTextureRegion[1])
        jumpAnimation = Animation(1f, *aTextureRegion[2])

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

        body.createFixture(fixtureDef)
        shape.dispose()

    }

    fun update(delta: Float) {
        world.step(Gdx.graphics.deltaTime, 6, 2)
        stateTime += Gdx.graphics.deltaTime

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            status = Status.JUMPING
            jump()
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!isJumping) status = Status.WALKING
            direction = Direction.LEFT
            x += SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (!isJumping) status = Status.WALKING
            direction = Direction.RIGHT
            x -= SPEED
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && !isJumping) { status = Status.IDlE }
//        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && isJumping) { status = Status.JUMPING }

        when (status) {
            Status.JUMPING -> {
                textureRegion = jumpAnimation.getKeyFrame(stateTime)
            }
            Status.WALKING -> {
                textureRegion = walkAnimation.getKeyFrame(stateTime)
            }
            Status.IDlE -> {
                textureRegion = idleAnimation.getKeyFrame(stateTime)
            }
        }

        when (direction) {
            Direction.LEFT -> {
                if (!textureRegion.isFlipX) textureRegion.flip(true, false)
            }
            Direction.RIGHT -> {
                if (textureRegion.isFlipX) textureRegion.flip(true, false)
            }
        }
    }

    fun draw(spriteBatch: SpriteBatch) {
        spriteBatch.draw(textureRegion, body.position.x - width / 2, body.position.y - height / 2, width, height)
    }

    private fun jump() {
//        if (!isJumping) {
            val position = body.position
            body.applyLinearImpulse(0f, 5000f, position.x, position.y, true)
//        }

    }

}