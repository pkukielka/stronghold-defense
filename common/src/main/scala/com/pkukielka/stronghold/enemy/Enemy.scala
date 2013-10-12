package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.{Intersector, Vector2}
import scala.language.implicitConversions
import com.pkukielka.stronghold.assets.EnemyAssets
import com.pkukielka.stronghold.IsometricMapUtils

abstract class Enemy(pathFinder: PathFinder) extends EnemyRenderer {
  var animationTime = Math.random().toFloat
  var xOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var yOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  val position = pathFinder.getFreePosition.add(xOffset, yOffset)
  var directionVector = new Vector2()
  var life = maxLife

  object OnFire {
    var isActive = false
    var timeLeft = 0f
    var damagePerSecond = 0f
  }

  object temp {
    val p1, p2, intersection = new Vector2()
  }

  def assets: EnemyAssets

  def maxLife: Float = 100f

  def velocity: Float

  def isDead = life <= 0

  def angle = (((247.5 - Math.toDegrees(Math.atan2(directionVector.y, directionVector.x))) % 360) / 45).toInt

  def setOnFire(time: Float, damagePerSecond: Float) {
    OnFire.isActive = true
    OnFire.timeLeft = time
    OnFire.damagePerSecond = damagePerSecond
    setOnFire(time)
  }

  private def isIntersectingPath(segmentStart: Vector2, segmentEnd: Vector2, p1x: Float, p1y: Float, p2x: Float, p2y: Float) = {
    temp.p1.set(p1x, p1y)
    temp.p2.set(p2x, p2y)
    Intersector.intersectSegments(segmentStart, segmentEnd, temp.p1, temp.p2, temp.intersection)
  }

  def isHit(segmentStart: Vector2, segmentEnd: Vector2): Boolean = {
    val pos = IsometricMapUtils.mapToCameraCoordinates(position.x, position.y)

    isIntersectingPath(segmentStart, segmentEnd, pos.x, pos.y, pos.x + width, pos.y) ||
      isIntersectingPath(segmentStart, segmentEnd, pos.x, pos.y, pos.x, pos.y + height) ||
      isIntersectingPath(segmentStart, segmentEnd, pos.x + width, pos.y, pos.x + width, pos.y + height) ||
      isIntersectingPath(segmentStart, segmentEnd, pos.x, pos.y + height, pos.x + width, pos.y + height)
  }

  def hit(damage: Float) {
    if (!isDead) {
      life -= damage
      if (isDead) {
        animationTime = 0
        assets.sound.die.play()
      }
      else {
        if (damage > maxLife * 0.1f) {
          assets.sound.hit.play()
        }
      }
    }
  }

  def move(deltaTime: Float) {
    val next = pathFinder.getNextStep(position)
    directionVector.set(
      pathFinder.map.node2posX(next) + xOffset - position.x,
      pathFinder.map.node2posY(next) + yOffset - position.y
    ).nor()
    directionVector.scl(velocity * deltaTime)
    position.add(directionVector)

    if (position.epsilonEquals(pathFinder.target, 1f)) {
      position.set(pathFinder.getFreePosition)
      if (Math.random() > 0.95) {
        assets.sound.ment.play()
      }
    }
  }

  def update(deltaTime: Float) {
    if (deltaTime < 0.10) {
      animationTime += deltaTime

      if (OnFire.isActive) {
        import OnFire._

        timeLeft -= deltaTime
        hit(deltaTime * damagePerSecond)

        if (timeLeft <= 0) {
          timeLeft = 0f
          isActive = false
          damagePerSecond = 0f
        }
      }

      if (!isDead) {
        move(deltaTime)
      }
    }
  }

}

