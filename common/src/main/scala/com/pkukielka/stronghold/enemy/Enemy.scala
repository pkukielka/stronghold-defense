package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.Vector2
import scala.language.implicitConversions
import com.pkukielka.stronghold.enemy.assets.Assets

abstract class Enemy(val assets: Assets, pathFinder: PathFinder) extends EnemyRenderer {
  var animationTime = Math.random().toFloat
  var xOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var yOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var position = pathFinder.getFreePosition.add(xOffset, yOffset)
  var directionVector = new Vector2()
  var life = maxLife

  object OnFire {
    var isActive = false
    var timeLeft = 0f
    var damagePerSecond = 0f
  }

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
      pathFinder.node2posX(next) + xOffset - position.x,
      pathFinder.node2posY(next) + yOffset - position.y
    ).nor()
    directionVector.scl(velocity * deltaTime)
    position.add(directionVector)

    if (position.epsilonEquals(pathFinder.target, 1f)) {
      position = pathFinder.getFreePosition
      if (Math.random() > 0.95) {
        assets.sound.ment.play()
      }
    }
  }

  def update(deltaTime: Float) {
    if (deltaTime < 0.05) {
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

