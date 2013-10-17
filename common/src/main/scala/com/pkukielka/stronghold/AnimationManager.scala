package com.pkukielka.stronghold

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.maps.tiled.TiledMap
import scala.Array
import com.pkukielka.stronghold.enemy.units._
import com.pkukielka.stronghold.enemy.{EnemyCore, EnemyCoreRenderer, PathFinder}
import com.pkukielka.stronghold.tower.{Tower, Attack, Renderer}
import com.badlogic.gdx.math.Vector2
import com.pkukielka.stronghold.tower.archer.{ArcherTowerRenderer, ArcherTower}

class AnimationManager(camera: OrthographicCamera, map: TiledMap, mapName: String) {
  private val attack = Gdx.audio.newSound(Gdx.files.internal("data/sound/powers/shoot.ogg"))
  private val shapeRenderer = new ShapeRenderer()

  private val mapBuilder = new MapBuilder(map)
  private implicit val influencesManager = new InfluencesManager(mapBuilder.width, mapBuilder.height)
  private implicit val pathFinder = new PathFinder(mapBuilder.getNode(7, 29), mapBuilder, influencesManager)

  val bullets = ArrayBuffer.empty[Attack]
  val towers = ArrayBuffer.empty[Tower]
  var totalTime = 0.0f

  pathFinder.update

  private val enemies: Array[EnemyCore] = ArrayBuffer.fill(10)(Array(
    new Skeleton with EnemyCoreRenderer, new SkeletonArcher with EnemyCoreRenderer,
    new SkeletonMage with EnemyCoreRenderer, new SkeletonWeak with EnemyCoreRenderer,
    new Goblin with EnemyCoreRenderer, new EliteGoblin with EnemyCoreRenderer,
    new EarthAnt with EnemyCoreRenderer, new FireAnt with EnemyCoreRenderer, new IceAnt with EnemyCoreRenderer,
    new Minotaur with EnemyCoreRenderer, new Stealth with EnemyCoreRenderer, new Zombie with EnemyCoreRenderer,
    new WyvernWater with EnemyCoreRenderer, new WyvernFire with EnemyCoreRenderer,
    new WyvernAir with EnemyCoreRenderer, new WyvernEarth with EnemyCoreRenderer
  )).flatten.toArray

  def hit(x: Float, y: Float) {
    attack.play()
    towers += new ArcherTower(new Vector2(IsometricMapUtils.cameraToMapX(x, y), IsometricMapUtils.cameraToMapY(x, y)))
      with ArcherTowerRenderer
  }

  def update(batch: SpriteBatch, deltaTime: Float) {
    val delta = deltaTime

    influencesManager.update(delta)

    enemies.sortBy(_.position.y)

    for (enemy <- enemies) {
      enemy.update(delta)
    }

    for (bullet <- bullets) {
      bullet.update(delta, enemies, pathFinder)
    }

    for (tower <- towers) {
      tower.update(delta, enemies, bullets)
    }

    batch.begin()
    for (enemy <- enemies if enemy.isDead) {
      enemy.asInstanceOf[EnemyCoreRenderer].drawModel(batch, delta)
    }
    batch.end()

    batch.begin()
    for (bullet <- bullets if bullet.isCompleted) {
      bullet.asInstanceOf[Renderer].draw(batch)
    }
    batch.end()

    batch.begin()
    for (enemy <- enemies if !enemy.isDead) {
      enemy.asInstanceOf[EnemyCoreRenderer].drawModel(batch, delta)
    }
    batch.end()

    batch.begin()
    for (tower <- towers) {
      tower.asInstanceOf[Renderer].draw(batch)
    }
    batch.end()

    batch.begin()
    for (bullet <- bullets if !bullet.isCompleted) {
      bullet.asInstanceOf[Renderer].draw(batch)
    }
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    for (enemy <- enemies) {
      enemy.asInstanceOf[EnemyCoreRenderer].drawLifeBar(shapeRenderer)
    }
    shapeRenderer.end()
  }
}
