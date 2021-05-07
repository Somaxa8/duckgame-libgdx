package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.*
import com.somacode.duckgame.Skills


class Entity(private val world: World) {
    val SPEED = 3f
    val width = 25f
    val height = 25f

    enum class Direction { DOWN, LEFT, RIGHT }
    enum class Status { IDlE, WALKING }
    var lastDireccion: Direction = Direction.LEFT
    var status: Status = Status.IDlE

    var body: Body
    lateinit var textureRegion: TextureRegion
    var walkAnimation: Animation<TextureRegion>
    var idleAnimation: Animation<TextureRegion>
    var x: Float = Gdx.graphics.width / 2f - width / 2f
    var y: Float = 0f
    var direction: Direction = Direction.LEFT
    var stateTime: Float = 0f
    var skills: Skills

    init {
        // Animations
        val texture = Texture("duck.png")
        val textureRegion = TextureRegion.split(texture, 25, 25)
        idleAnimation = Animation(0.1f, textureRegion[0][0])
        walkAnimation = Animation(0.1f, *textureRegion[1])

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

        skills = Skills(body)

    }

    fun update(delta: Float) {
        skills.update(delta)
        world.step(Gdx.graphics.deltaTime, 6, 2)
        stateTime += Gdx.graphics.deltaTime

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            status = Status.WALKING
            direction = Direction.LEFT
            x += SPEED
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            status = Status.WALKING
            direction = Direction.RIGHT
            x -= SPEED
        }


//        when (direction) {
//            Direction.LEFT -> {
//                  textureRegion = walkAnimation.getKeyFrame(stateTime)
//                if (!textureRegion.isFlipX) {
//                    textureRegion.flip(true, false)
//                }
//            }
//            Direction.RIGHT -> {
//                  textureRegion = walkAnimation.getKeyFrame(stateTime)
//                if (textureRegion.isFlipX) {
//                    textureRegion.flip(true, false)
//                }
//            }
//        }

        if (!Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) { status = Status.IDlE }

        when (status) {
            Status.WALKING -> {
                textureRegion = walkAnimation.getKeyFrame(stateTime)
            }
            Status.IDlE -> {
                textureRegion = idleAnimation.getKeyFrame(stateTime)
            }
        }

        if (direction != lastDireccion) {
            println( "$direction es o no igual $lastDireccion")
            textureRegion.flip(, false)
        }

        lastDireccion = direction
    }

    fun draw(spriteBatch: SpriteBatch) {
        spriteBatch.draw(textureRegion, body.position.x - width / 2, body.position.y - height / 2, width, height)
    }

}