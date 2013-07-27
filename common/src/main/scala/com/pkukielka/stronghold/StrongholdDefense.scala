package com.pkukielka.stronghold

import com.badlogic.gdx.{InputMultiplexer, Game, Gdx}
import com.badlogic.gdx.graphics.{OrthographicCamera, GL10}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.input.GestureDetector
import scala.language.implicitConversions
import com.pkukielka.stronghold.enemy._

class StrongholdDefense extends Game {

  private var map: TiledMap = null
  private var renderer: IsometricTiledMapRenderer = null
  private var camera: OrthographicCamera = null
  private var font: BitmapFont = null
  private var batch: SpriteBatch = null

  private var animationManager: AnimationManager = null

  override def create {
    camera = new OrthographicCamera
    camera.setToOrtho(false, (Gdx.graphics.getWidth().toFloat / Gdx.graphics.getHeight().toFloat) * 10, 10)
    camera.update()

    font = new BitmapFont()
    batch = new SpriteBatch()

    map = new TmxMapLoader().load("data/maps/test.tmx")
    renderer = new IsometricTiledMapRenderer(map, 1f / 64f)

    animationManager = new AnimationManager(camera, map, "test")

    val inputCamController = new InputCamController(camera)
    val gestureCamController = new GestureDetector(new GestureCamController(camera, map, animationManager.hit))
    val inputMultiplexer = new InputMultiplexer(gestureCamController, inputCamController)
    Gdx.input.setInputProcessor(inputMultiplexer)
  }

  override def render {
    Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f)
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

    camera.update()
    renderer.setView(camera)
    renderer.render()
    animationManager.update(renderer.getSpriteBatch, Gdx.graphics.getDeltaTime())

    batch.begin()
    font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20)
    batch.end()
  }

  def needsGL20: Boolean = {
    true
  }

  override def dispose {
    map.dispose()
  }
}
