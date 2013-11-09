package com.pkukielka.stronghold.spell.attacks.arrow

import com.badlogic.gdx.math._
import com.pkukielka.stronghold.spell.Attack
import com.pkukielka.stronghold.enemy.Enemy
import com.pkukielka.stronghold.IsometricMapUtils
import com.pkukielka.stronghold.assets.Assets

object Arrow  {
  val maxMiss = 2f
  val range = 20f
  val baseDamage = 10f
  val randomDamage = 20f
  val velocity = 20f
  val heightRangeIncrease = 1.2f
  val assets = Assets.spellSpecial2
}

class Arrow extends Attack {
  protected val position: Vector2 = new Vector2()
  protected val previousPosition = new Vector2()
  protected val direction: Vector2 = new Vector2()
  private var heightsDifference: Float = 0f
  private val path = new Bezier[Vector2]
  private var timeToComplete = 0f

  object temp {
    val middle: Vector2 = new Vector2()
    val target: Vector2 = new Vector2()
    val p1, p2: Vector2 = new Vector2()
  }

  import Arrow._

  override protected def lifeTime = timeToComplete

  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    activate()

    position.set(IsometricMapUtils.mapToCameraX(xStart, yStart), IsometricMapUtils.mapToCameraY(xStart, yStart))
    temp.target.set(IsometricMapUtils.mapToCameraX(xEnd, yEnd), IsometricMapUtils.mapToCameraY(xEnd, yEnd))
    this.heightsDifference = heightsDifference

    val distance = position.dst(temp.target)
    val currentMiss = (distance / range) * maxMiss
    val heightAddition = (distance / range) * distance * 0.5f

    temp.target.add(((0.5 - scala.math.random) * currentMiss).toFloat, ((0.5 - scala.math.random) * currentMiss).toFloat)
    temp.middle.set(position).add(temp.target).scl(0.5f).add(0, heightAddition)
    path.set(position, temp.middle, temp.target)
    direction.set(temp.target).sub(position).nor()

    time = 0f
    timeToComplete = distance / velocity
  }

  private def updatePosition() = {
    previousPosition.set(position)
    path.valueAt(position, time / timeToComplete)
    direction.set(position).sub(previousPosition).nor()
  }

  private def updateWorldState(enemies: Array[Enemy]): Unit = {
    temp.p1.set(IsometricMapUtils.cameraToMapX(previousPosition), IsometricMapUtils.cameraToMapY(previousPosition))
    temp.p2.set(IsometricMapUtils.cameraToMapX(position), IsometricMapUtils.cameraToMapY(position))
    for (enemy <- enemies if !enemy.isDead && enemy.isHit(temp.p1, temp.p2)) {
      enemy.hit((baseDamage + scala.math.random * randomDamage).toInt)
      return
    }
  }

  def update(deltaTime: Float, enemies: Array[Enemy]) {
    if (isActive) {
      time = (time + deltaTime).min(timeToComplete)
      updatePosition()
      updateWorldState(enemies)
    }

    if (time >= timeToComplete) {
      deactivate()
    }
  }
}
