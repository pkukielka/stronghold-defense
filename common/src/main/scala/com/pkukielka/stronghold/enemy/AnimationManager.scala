package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.maps.tiled.TiledMap
import scala.Array
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.{InfluencesManager, MapBuilder, IsometricMapUtils}
import com.pkukielka.stronghold.tower.bullet.{FireArrow, Bullet, Arrow}

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

  private val mapBuilder = new MapBuilder(map)
  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder, influencesManager)

  val bullets = ArrayBuffer.empty[Bullet]

  def updatePath() = pathFinder.findShortestPathsTo(pathFinder.getNode(7, 29))

  updatePath()

  private val enemies = ArrayBuffer.fill(10)(Array(
    new Skeleton, new SkeletonArcher, new SkeletonMage, new SkeletonWeak,
    new Goblin, new EliteGoblin, new EarthAnt, new FireAnt, new IceAnt,
    new Minotaur, new Stealth, new Zombie,
    new WyvernWater, new WyvernFire, new WyvernAir, new WyvernEarth
  )).flatten

  def hit(x: Float, y: Float) {
    attack.play()

    val arrow = if ((bullets.size + 1) % 2 == 0) {
      new FireArrow()
    }
    else {
      new Arrow()
    }

    arrow.init(12, 3, x, y, 0)
    bullets += arrow
  }

  def update(batch: SpriteBatch, deltaTime: Float) {
    val delta = deltaTime

    influencesManager.update(delta)

    enemies.foreach(_.update(delta))

    bullets.foreach {
      bullet =>
        bullet.update(delta)

        if (!bullet.isCompleted) {
          enemies.foreach {
            enemy =>
              if (!enemy.isDead && enemy.isHit(bullet.position.x, bullet.position.y, utils)) {
                enemy.hit((30 + Math.random() * 50).toInt)
                if (bullet.isInstanceOf[FireArrow]) {
                  enemy.setOnFire(1 + Math.random().toFloat * 4, 20)
                }
                if (enemy.isDead) {
                  influencesManager.add(10000f, enemy.position, 1000f, 200, 3f)
                  updatePath()
                }
              }
          }
        }
    }

    batch.begin()
    enemies.filter(_.isDead).foreach(_.drawModel(batch, delta, utils))
    batch.end()

    batch.begin()
    bullets.filter(_.isCompleted).foreach(_.draw(batch))
    batch.end()

    batch.begin()
    enemies.filter(!_.isDead).foreach(_.drawModel(batch, delta, utils))
    batch.end()

    batch.begin()
    bullets.filter(!_.isCompleted).foreach(_.draw(batch))
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    enemies.foreach(_.drawLifeBar(shapeRenderer, utils))
    shapeRenderer.end()
  }
}
