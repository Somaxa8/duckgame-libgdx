package com.somacode.duckgame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.somacode.duckgame.util.Drawable
import com.somacode.duckgame.util.Updatable

class Camera(private val player: Entity) : Updatable, Drawable {
    private val OFFSET_Y = 60
    var camera: OrthographicCamera? = null
    var x = 0f
    var playerX = 0f


    init {
        val w = Gdx.graphics.width.toFloat()
        val h = Gdx.graphics.height.toFloat()
        camera = OrthographicCamera(w, h)
        camera!!.zoom = 0.6f
        x = player.x + player.WIDTH / 2f
    }

    override fun update(delta: Float) {
        playerX = player.x + player.WIDTH / 2f

        if (playerX > x) x += (playerX - x) * 10 * delta
        if (playerX < x) x -= (x - playerX) * 10 * delta

        camera!!.position[x, player.y + OFFSET_Y + player.HEIGHT / 2f] = 0f
        camera!!.zoom = 0.9f
        camera!!.update()
    }

    override fun draw(spriteBatch: SpriteBatch) {
        spriteBatch.projectionMatrix = camera!!.combined
    }
}