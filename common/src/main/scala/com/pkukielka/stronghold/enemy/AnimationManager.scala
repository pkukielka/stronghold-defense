package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.{Color, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.maps.tiled.TiledMap
import scala.Array
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.{InfluencesManager, MapBuilder, IsometricMapUtils}

object AnimationManager {

  object lifeBar {
    val width = 0.4f
    val height = 0.06f
    val margin = 0.02f
    val distance = 0.1f
  }

}

class AnimationManager(camera: OrthographicCamera, map: TiledMap, mapName: String) {
  private val utils = new IsometricMapUtils(camera)
  private val attack = Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/shoot.ogg"))
  private val shapeRenderer = new ShapeRenderer()
  private val unitScale = 1 / 96f

  private val mapBuilder = new MapBuilder(map)
  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder, influencesManager)

  def updatePath() = pathFinder.findShortestPathsTo(pathFinder.getNode(7, 29))

  updatePath()

  private val enemies = ArrayBuffer.fill(10)(Array(
    new Skeleton, new SkeletonArcher, new SkeletonMage, new SkeletonWeak,
    new Goblin, new EliteGoblin, new EarthAnt, new FireAnt, new IceAnt,
    new Minotaur, new Stealth, new Zombie,
    new WyvernWater, new WyvernFire , new WyvernAir, new WyvernEarth
  )).flatten

  def hit(x: Float, y: Float) {
    def isHit(x: Float, y: Float, enemy: Enemy): Boolean = {
      val position = utils.getScreenCoordinates(enemy.position.x, enemy.position.y)
      position.x <= x && position.x + getEnemyWidth(enemy) >= x &&
        position.y <= y && position.y + getEnemyWidth(enemy) >= y
    }

    attack.play()
    enemies.foreach {
      enemy =>
        if (!enemy.isDead && isHit(x, y, enemy)) {
          enemy.hit((30 + Math.random() * 50).toInt)

          if (enemy.isDead) {
            influencesManager.add(10000f, enemy.position, 1000f, 200, 3f)
          }
        }
    }
    updatePath()
  }

  def getEnemyWidth(enemy: Enemy) = enemy.currentFrame.getRegionWidth * unitScale

  def getEnemyHeight(enemy: Enemy) = enemy.currentFrame.getRegionHeight * unitScale

  def drawLifeBar(enemy: Enemy) {
    import AnimationManager.lifeBar._

    if (!enemy.isDead && enemy.life != enemy.maxLife) {
      val position = utils.getScreenCoordinates(enemy.position.x, enemy.position.y)
      val x = position.x + (getEnemyWidth(enemy) - 0.5f) / 2
      val y = position.y + getEnemyHeight(enemy) + distance
      val lifeRatio = enemy.life.toFloat / enemy.maxLife

      shapeRenderer.setColor(Color.BLACK)
      shapeRenderer.rect(x, y, width, height)
      shapeRenderer.setColor(1.2f - Math.pow(lifeRatio, 2).toFloat, 1f - Math.pow(1f - lifeRatio, 2).toFloat - 0.2f, 0f, 1f)
      shapeRenderer.rect(x + margin, y + margin, lifeRatio * (width - 2 * margin), height - 2 * margin)
    }
  }

  def drawEnemy(enemy: Enemy, batch: SpriteBatch, deltaTime: Float) {
    enemy.update(deltaTime)
    val position = utils.getScreenCoordinates(enemy.position.x, enemy.position.y)
    val frame = enemy.currentFrame
    batch.draw(frame, position.x, position.y, frame.getRegionWidth * unitScale, frame.getRegionHeight * unitScale)
  }

  def update(batch: SpriteBatch, deltaTime: Float) {
    val delta = deltaTime

    influencesManager.update(delta)

    batch.begin()
    enemies.filter(_.isDead) foreach (drawEnemy(_, batch, delta))
    batch.end()

    batch.begin()
    enemies.filter(!_.isDead) foreach (drawEnemy(_, batch, delta))
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    enemies.foreach(drawLifeBar)
    shapeRenderer.end()
  }
}
