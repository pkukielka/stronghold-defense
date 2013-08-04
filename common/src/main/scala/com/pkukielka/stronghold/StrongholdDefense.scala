package com.pkukielka.stronghold

import com.badlogic.gdx.{InputMultiplexer, Game, Gdx}
import com.badlogic.gdx.graphics.{Texture, OrthographicCamera, GL10}
import com.badlogic.gdx.graphics.g2d.{BitmapFont, SpriteBatch}
import com.badlogic.gdx.maps.tiled.{TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import com.badlogic.gdx.input.GestureDetector
import scala.language.implicitConversions
import com.pkukielka.stronghold.enemy._
import com.pkukielka.stronghold.assets.Assets

class StrongholdDefense extends Game {

  private var map: TiledMap = null
  private var renderer: IsometricTiledMapRenderer = null
  private var camera: OrthographicCamera = null
  private var font: BitmapFont = null
  private var batch: SpriteBatch = null

  private var splash: Texture = null
  private var animationManager: AnimationManager = null

  private var isLoaded = false

  override def create() {
    camera = new OrthographicCamera
    camera.setToOrtho(false, (Gdx.graphics.getWidth.toFloat / Gdx.graphics.getHeight.toFloat) * 10, 10)
    camera.update()

    font = new BitmapFont()
    batch = new SpriteBatch()

    map = new TmxMapLoader().load("data/maps/test.tmx")
    renderer = new IsometricTiledMapRenderer(map, 1f / 64f)

    splash = new Texture(Gdx.files.internal("data/textures/splash.png"))
  }

  override def render() {
    Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f)
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

    if (!isLoaded) {
      if (Assets.update()) {
        animationManager = new AnimationManager(camera, map, "test")

        val inputCamController = new InputCamController(camera)
        val gestureCamController = new GestureDetector(new GestureCamController(camera, animationManager.hit))
        Gdx.input.setInputProcessor(new InputMultiplexer(gestureCamController, inputCamController))

        isLoaded = true
      }
      batch.begin()
      batch.draw(splash, 0, 0, Gdx.graphics.getWidth, Gdx.graphics.getHeight)
      batch.end()

      return
    }

    camera.update()

    renderer.setView(camera)
    renderer.render()
    animationManager.update(renderer.getSpriteBatch, Gdx.graphics.getDeltaTime)

    batch.begin()
    font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond, 10, 20)
    batch.end()
  }

  def needsGL20: Boolean = {
    true
  }

  override def dispose() {
    map.dispose()
  }
}
