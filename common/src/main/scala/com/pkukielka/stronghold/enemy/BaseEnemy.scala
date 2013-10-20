package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.{Intersector, Vector2}
import scala.language.implicitConversions

abstract class BaseEnemy(pathFinder: PathFinder) extends Enemy {
  var animationTime = scala.math.random.toFloat
  var xOffset = (0.5f + (scala.math.random - 0.5f) / 4).toFloat
  var yOffset = (0.5f + (scala.math.random - 0.5f) / 4).toFloat
  val position =  pathFinder.getFreePosition.add(xOffset, yOffset)
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

  override def width: Float = 1f

  override def height: Float = 1f

  override def setOnFire(time: Float, damagePerSecond: Float) {
    OnFire.isActive = true
    OnFire.timeLeft = time
    OnFire.damagePerSecond = damagePerSecond
  }

  override def die() {
    animationTime = 0
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

  protected def maxLife: Float = 100f // TODO: set in base classes

  private def isIntersectingPath(segmentStart: Vector2, segmentEnd: Vector2, p1x: Float, p1y: Float, p2x: Float, p2y: Float) = {
    temp.p1.set(p1x, p1y)
    temp.p2.set(p2x, p2y)
    Intersector.intersectSegments(segmentStart, segmentEnd, temp.p1, temp.p2, temp.intersection)
  }

  private def move(deltaTime: Float) {
    val next = pathFinder.getNextStep(position)

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

