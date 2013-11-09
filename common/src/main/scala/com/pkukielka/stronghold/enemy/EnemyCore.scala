package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.{Intersector, Vector2}
import scala.language.implicitConversions

abstract class EnemyCore(pathFinder: PathFinder) extends Enemy {
  protected var life = maxLife
  protected val baseSize = (1.0f - (scala.math.random * 0.2f)).toFloat
  protected val xOffset = (0.5f + (scala.math.random - 0.5f) / 4).toFloat
  protected val yOffset = (0.5f + (scala.math.random - 0.5f) / 4).toFloat
  val position = pathFinder.getFreePosition.add(xOffset, yOffset)
  val directionVector = new Vector2()
  var isHold = false

  advanceTime(scala.math.random.toFloat)

  object temp {
    val p1, p2, intersection = new Vector2()
  }

  override protected def lifeTime = Float.MaxValue

  override def width: Float = baseSize

  override def height: Float = baseSize

  override def die() {
    resetTime()
  }

  override def harm(damage: Float) {
  }

  override def turn() {
    position.set(pathFinder.getFreePosition)
  }

  override def hit(damage: Float) {
    if (!isDead) {
      life -= damage

      if (isDead) {
        die()
      }
      else {
        harm(damage)
      }
    }
  }

  override def isDead = life <= 0

  override def isHit(segmentStart: Vector2, segmentEnd: Vector2): Boolean = {
    isIntersectingPath(segmentStart, segmentEnd, position.x, position.y, position.x + width, position.y) ||
      isIntersectingPath(segmentStart, segmentEnd, position.x, position.y, position.x, position.y + height) ||
      isIntersectingPath(segmentStart, segmentEnd, position.x + width, position.y, position.x + width, position.y + height) ||
      isIntersectingPath(segmentStart, segmentEnd, position.x, position.y + height, position.x + width, position.y + height)
  }

  override def update(deltaTime: Float) {
    advanceTime(deltaTime)

    if (!isDead) {
      move(deltaTime)
    }

    isHold = false
  }

  protected def maxLife: Float = 100f // TODO: set in base classes

  private def isIntersectingPath(segmentStart: Vector2, segmentEnd: Vector2, p1x: Float, p1y: Float, p2x: Float, p2y: Float) = {
    temp.p1.set(p1x, p1y)
    temp.p2.set(p2x, p2y)
    Intersector.intersectSegments(segmentStart, segmentEnd, temp.p1, temp.p2, temp.intersection)
  }

  private def move(deltaTime: Float) {
    val next = pathFinder.getNextStep(position)

    // TODO: This is safety check, but we should try to make it unnecessary
    if (next == pathFinder.map.getNode(position)) {
      hit(1000)
    }
    else if (!isHold) {
      directionVector.set(
        pathFinder.map.node2posX(next) + xOffset - position.x,
        pathFinder.map.node2posY(next) + yOffset - position.y
      ).nor().scl(velocity * deltaTime)

      position.add(directionVector)

      if (position.epsilonEquals(pathFinder.target, 1f)) {
        turn()
      }
    }
  }

}

