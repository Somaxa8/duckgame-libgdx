package com.somacode.duckgame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.ContactFilter
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.World

class Main : ApplicationAdapter() {
    private var batch: SpriteBatch? = null
    private var entity: Entity? = null
    private var floor: Floor? = null
    private var camera: Camera? = null
    var world: World? = null

    override fun create() {
        batch = SpriteBatch()

        world = World(Vector2(0f, -10f), true)
        world.setContactFilter()
        entity = Entity(world!!)
        floor = Floor(world!!)
        camera = Camera(entity!!)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        val delta = Gdx.graphics.deltaTime

        camera!!.update(delta)
        batch!!.begin()
        camera!!.draw(batch!!)
        entity!!.render(batch!!)
        floor!!.render(batch!!)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        world!!.dispose()
    }
}