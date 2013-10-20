package com.pkukielka.stronghold

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.maps.tiled.TiledMap
import scala.Array
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.enemy._
import com.pkukielka.stronghold.tower.{Tower, Attack}
import com.badlogic.gdx.math.Vector2
import com.pkukielka.stronghold.tower.archer.{ArcherTowerRenderer, ArcherTower}
import com.pkukielka.stronghold.utils.Sorting

class AnimationManager(camera: OrthographicCamera, map: TiledMap, mapName: String) {
  private val shapeRenderer = new ShapeRenderer()

  private val mapBuilder = new MapBuilder(map)
  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder.getNode(7, 29), mapBuilder, influencesManager)

  val bullets = ArrayBuffer.empty[Attack]
  val towers = ArrayBuffer.empty[Tower with Renderer]
  var totalTime = 0.0f

  pathFinder.update

  private val enemies: Array[Enemy with Renderer] = ArrayBuffer.fill(10)(Array(
    new Skeleton with EnemyRenderer, new SkeletonArcher with EnemyRenderer,
    new SkeletonMage with EnemyRenderer, new SkeletonWeak with EnemyRenderer,
    new Goblin with EnemyRenderer, new EliteGoblin with EnemyRenderer,
    new EarthAnt with EnemyRenderer, new FireAnt with EnemyRenderer, new IceAnt with EnemyRenderer,
    new Minotaur with EnemyRenderer, new Stealth with EnemyRenderer, new Zombie with EnemyRenderer,
    new WyvernWater with EnemyRenderer, new WyvernFire with EnemyRenderer,
    new WyvernAir with EnemyRenderer, new WyvernEarth with EnemyRenderer
  )).flatten.toArray

  var enemiesWithTowers: ArrayBuffer[Renderer] = enemies.map(_.asInstanceOf[Renderer]).to[ArrayBuffer]

  def hit(x: Float, y: Float) {
    val tower = new ArcherTower(
      new Vector2(IsometricMapUtils.cameraToMapX(x, y),
        IsometricMapUtils.cameraToMapY(x, y)), pathFinder)  with ArcherTowerRenderer
    towers += tower
    enemiesWithTowers += tower
  }

  def update(batch: SpriteBatch, deltaTime: Float) {
    val delta = deltaTime

    influencesManager.update(delta)

    Sorting.sort(enemiesWithTowers, 0, enemiesWithTowers.length)

    for (enemy <- enemies) {
      enemy.update(delta)
    }
    for (bullet <- bullets) {
      bullet.update(delta, enemies.asInstanceOf[Array[Enemy]], pathFinder)
    }
    for (tower <- towers) {
      tower.update(delta, enemies.asInstanceOf[Array[Enemy]], bullets)
    }

    batch.begin()
    for (bullet <- bullets if bullet.isCompleted) {
      bullet.asInstanceOf[Renderer].draw(batch, delta)
    }
    for (o <- enemiesWithTowers) {
      if (!o.isInstanceOf[Enemy] || o.asInstanceOf[Enemy].isDead) o.draw(batch, delta)
    }
    for (o <- enemiesWithTowers) {
      if (!o.isInstanceOf[Enemy] || !o.asInstanceOf[Enemy].isDead) o.draw(batch, delta)
    }
    for (bullet <- bullets if !bullet.isCompleted) {
      bullet.asInstanceOf[Renderer].draw(batch, delta)
    }
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    for (enemy <- enemies) {
      enemy.asInstanceOf[EnemyRenderer].drawLifeBar(shapeRenderer)
    }
    shapeRenderer.end()
  }
}
