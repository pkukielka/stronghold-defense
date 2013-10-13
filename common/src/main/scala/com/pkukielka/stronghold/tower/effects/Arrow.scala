package com.pkukielka.stronghold.tower.effects

import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.badlogic.gdx.math._
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.pkukielka.stronghold.tower.Effect
import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.IsometricMapUtils

object Arrow {
  val maxMiss = 1f
  val maxRange = 20f
  val baseDamage = 30f
  val randomDamage = 50f
  val velocity = 30f
  val heightRangeIncrease = 1.2f
  val spriteScale = 1 / 256f

  val arrow_broken_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow_broken.png")))
  val arrow_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow.png")))
}

class Arrow extends Effect {
  protected val position: Vector2 = new Vector2()
  protected val previousPosition = new Vector2()
  protected val direction: Vector2 = new Vector2()
  private var heightsDifference: Float = 0f
  private val path = new Bezier[Vector2]
  private var time = 0f
  private var timeToComplete = 0f

  object temp {
    val middle: Vector2 = new Vector2()
    val target: Vector2 = new Vector2()
    val p1, p2: Vector2 = new Vector2()
  }

  import Arrow._

  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    position.set(IsometricMapUtils.mapToCameraX(xStart, yStart), IsometricMapUtils.mapToCameraY(xStart, yStart))
    temp.target.set(IsometricMapUtils.mapToCameraX(xEnd, yEnd), IsometricMapUtils.mapToCameraY(xEnd, yEnd))
    this.heightsDifference = heightsDifference

    val distance = position.dst(temp.target)
    val currentMiss = (distance / maxRange) * maxMiss
    val heightAddition = (distance / maxRange) * distance * 0.5f

    temp.target.add(((0.5 - Math.random()) * currentMiss).toFloat, ((0.5 - Math.random()) * currentMiss).toFloat)
    temp.middle.set(position).add(temp.target).scl(0.5f).add(0, heightAddition)
    path.set(position, temp.middle, temp.target)

    time = 0f
    timeToComplete = distance / velocity
  }

  def isCompleted = time == timeToComplete

  private def angle = Math.toDegrees(Math.atan2(direction.y, direction.x)).toFloat

  private def updatePosition() = {
    previousPosition.set(position)
    path.valueAt(position, time / timeToComplete)
    direction.set(position).sub(previousPosition).nor()
  }

  private def updateWorldState(enemies: Array[Enemy], pathFinder: PathFinder): Unit = {
    temp.p1.set(IsometricMapUtils.cameraToMapX(previousPosition), IsometricMapUtils.cameraToMapY(previousPosition))
    temp.p2.set(IsometricMapUtils.cameraToMapX(position), IsometricMapUtils.cameraToMapY(position))
    for (enemy <- enemies if !enemy.isDead && enemy.isHit(temp.p1, temp.p2)) {
      enemy.hit((baseDamage + Math.random() * randomDamage).toInt)
      if (enemy.isDead) {
        pathFinder.influencesManager.add(10000f, enemy.position, 1000f, 200, 3f)
        pathFinder.update
      }
      return
    }
  }

  def update(deltaTime: Float, enemies: Array[Enemy], pathFinder: PathFinder) {
    if (!isCompleted) {
      time = (time + deltaTime).min(timeToComplete)
      updatePosition()
      updateWorldState(enemies, pathFinder)
    }
  }

  def draw(batch: SpriteBatch) {
    if (isCompleted) {
      batch.draw(arrow_broken_sprite, position.x, position.y, 0, 0,
        arrow_broken_sprite.getWidth, arrow_broken_sprite.getHeight, spriteScale, spriteScale, angle)
    }
    else {
      batch.draw(arrow_sprite, position.x, position.y, 0, 0,
        arrow_sprite.getWidth, arrow_sprite.getHeight, spriteScale, spriteScale, angle)
    }
  }
}
