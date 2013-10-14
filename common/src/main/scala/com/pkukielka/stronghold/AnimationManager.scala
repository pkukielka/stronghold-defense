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
import com.pkukielka.stronghold.enemy.PathFinder
import com.pkukielka.stronghold.tower.attacks.{Arrow, FireArrow}
import com.pkukielka.stronghold.tower.{ArcherTower, Tower, Attack}
import com.badlogic.gdx.math.Vector2

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

  private val enemies = ArrayBuffer.fill(10)(Array(
    new Skeleton, new SkeletonArcher, new SkeletonMage, new SkeletonWeak,
    new Goblin, new EliteGoblin, new EarthAnt, new FireAnt, new IceAnt,
    new Minotaur, new Stealth, new Zombie,
    new WyvernWater, new WyvernFire, new WyvernAir, new WyvernEarth
  )).flatten.toArray

  def hit(x: Float, y: Float) {
    attack.play()
    towers += new ArcherTower(new Vector2(IsometricMapUtils.cameraToMapX(x, y), IsometricMapUtils.cameraToMapY(x, y)))
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
      enemy.drawModel(batch, delta)
    }
    batch.end()

    batch.begin()
    for (bullet <- bullets if bullet.isCompleted) {
      bullet.draw(batch)
    }
    batch.end()

    batch.begin()
    for (enemy <- enemies if !enemy.isDead) {
      enemy.drawModel(batch, delta)
    }
    batch.end()

    batch.begin()
    for (tower <- towers) {
      tower.draw(batch)
    }
    batch.end()

    batch.begin()
    for (bullet <- bullets if !bullet.isCompleted) {
      bullet.draw(batch)
    }
    batch.end()

    shapeRenderer.setProjectionMatrix(camera.combined)
    shapeRenderer.begin(ShapeType.Filled)
    for (enemy <- enemies) {
      enemy.drawLifeBar(shapeRenderer)
    }
    shapeRenderer.end()
  }
}
