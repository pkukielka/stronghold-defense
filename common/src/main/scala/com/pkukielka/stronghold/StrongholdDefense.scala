package com.pkukielka.stronghold

import com.badlogic.gdx.{InputMultiplexer, Game, Gdx}
import com.badlogic.gdx.graphics.GL10
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TiledMap, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer
import scala.Boolean
import com.badlogic.gdx.input.GestureDetector
import scala.language.implicitConversions
import scala.collection.convert.WrapAsScala.asScalaIterator
import com.badlogic.gdx.maps.MapLayer

class StrongholdDefense extends Game {

  private var map: TiledMap = null
  private var renderer: IsometricTiledMapRenderer = null
  private var camera: OrthographicCamera = null
  private var font: BitmapFont = null
  private var batch: SpriteBatch = null

  private var path: List[Int] = null
  private var mapGraph: MapGraph = null
  private var enemiesLayer: TiledMapTileLayer = null
  private var pathElem = 0.0f

  def findShortestPath(startNode: Int, endNode: Int): List[Int] = {
    val g = mapGraph.graph
    (g.get(startNode) shortestPathTo g.get(endNode)) match {
      case Some(p) => p.nodes.map(_.value.asInstanceOf[Int])
      case _ => List(startNode)
    }
  }

  def onTap(x: Int, y: Int) {
    def getLayer(layer: MapLayer) = layer.asInstanceOf[TiledMapTileLayer]
    map.getLayers.iterator().toList.reverse.find(getLayer(_).getCell(x, y) != null).foreach(getLayer(_).setCell(x, y, null))
    mapGraph.rebuildNode(x, y)
    path = null
  }

  override def create {
    camera = new OrthographicCamera
    camera.setToOrtho(false, (Gdx.graphics.getWidth().toFloat / Gdx.graphics.getHeight().toFloat) * 10, 10)
    camera.update()

    font = new BitmapFont()
    batch = new SpriteBatch()

    map = new TmxMapLoader().load("data/maps/terrain_test.tmx")
    renderer = new IsometricTiledMapRenderer(map, 1f / 64f)

    mapGraph = new MapGraph(map)
    enemiesLayer = map.getLayers.iterator().find(_.getName == "enemies").map(_.asInstanceOf[TiledMapTileLayer]).get

    val inputCamController = new InputCamController(camera)
    val gestureCamController = new GestureDetector(new GestureCamController(camera, map, onTap))
    val inputMultiplexer = new InputMultiplexer(gestureCamController, inputCamController)
    Gdx.input.setInputProcessor(inputMultiplexer)
  }

  def renderPath() {
    def getX = path(pathElem.toInt) % mapGraph.width
    def getY = path(pathElem.toInt) / mapGraph.width

    if (path == null) {
      path = findShortestPath(20, 877)
    }

    enemiesLayer.setCell(getX, getY, null)
    pathElem = (pathElem + 0.5f) % path.length
    enemiesLayer.setCell(getX, getY, new TiledMapTileLayer.Cell())
    enemiesLayer.getCell(getX, getY).setTile(map.getTileSets.getTile(104))
  }

  override def render {
    Gdx.gl.glClearColor(0.55f, 0.55f, 0.55f, 1f)
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
    camera.update()
    renderer.setView(camera)
    renderer.render()

    renderPath()

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
