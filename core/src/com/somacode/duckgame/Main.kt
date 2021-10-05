package com.somacode.duckgame

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.ContactFilter
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.World
import com.somacode.duckgame.tool.WorldContactListener

class Main : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var entity: Entity
    lateinit var floor: Floor
    lateinit var camera: Camera
    lateinit var world: World
    lateinit var box2DDebugRenderer: Box2DDebugRenderer

    override fun create() {
        batch = SpriteBatch()

        world = World(Vector2(0f, -100f), true)
        box2DDebugRenderer = Box2DDebugRenderer()
        entity = Entity(world)
        world.setContactListener(WorldContactListener(entity))
        floor = Floor(world)
        camera = Camera(entity)
    }

    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        box2DDebugRenderer.render(world, camera.camera.combined)

        val delta = Gdx.graphics.deltaTime

        camera.update(delta)
        entity.update(delta)

        batch.begin()
        camera.draw(batch)
        entity.draw(batch)
        floor.render(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        world.dispose()
    }
}