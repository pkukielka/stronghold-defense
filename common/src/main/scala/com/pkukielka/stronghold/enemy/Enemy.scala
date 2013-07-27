package com.pkukielka.stronghold.enemy

import com.badlogic.gdx.math.Vector3
import scala.language.implicitConversions
import com.pkukielka.stronghold.enemy.assets.Assets

abstract class Enemy(val assets: Assets, pathFinder: PathFinder) {
  var animationTime = Math.random().toFloat
  var xOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var yOffset = (0.5f + (Math.random() - 0.5f) / 4).toFloat
  var position = pathFinder.getFreePosition.add(xOffset, yOffset, 0.0f)
  var directionVector = new Vector3()
  var life = maxLife
  val model = new EnemyModel(this)

  def maxLife = 100

  def velocity: Float

  def isDead = life <= 0

  def angle = (((247.5 - Math.toDegrees(Math.atan2(directionVector.y, directionVector.x))) % 360) / 45).toInt

  def hit(damage: Int) {
    if (!isDead) {
      life -= damage
      if (isDead) {
        animationTime = 0
        assets.sound.die.play()
      }
      else {
        assets.sound.hit.play()
      }
    }
  }

  def move(deltaTime: Float) {
    val next = pathFinder.getNextStep(position)
    directionVector.set(
      pathFinder.node2posX(next) + xOffset - position.x,
      pathFinder.node2posY(next) + yOffset - position.y,
      0.0f).nor()
    directionVector.scl(velocity * deltaTime)
    position.add(directionVector)

    if (position.epsilonEquals(pathFinder.target, 1.0f)) {
      position = pathFinder.getFreePosition
      if (Math.random() > 0.95) {
        assets.sound.ment.play()
      }
    }
  }

  def update(deltaTime: Float) {
    if (deltaTime < 0.05) {
      animationTime += deltaTime
      if (!isDead) {
        move(deltaTime)
      }
    }
  }

}
