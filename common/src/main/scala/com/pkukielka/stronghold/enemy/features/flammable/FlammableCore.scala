package com.pkukielka.stronghold.enemy.features.flammable

import com.pkukielka.stronghold.enemy.Enemy

trait FlammableCore extends Enemy with Flammable {
  var timeLeft = 0f
  var damagePerSecond = 0f

  protected def isActive = timeLeft > 0

  override def setOnFire(timeLeft: Float, damagePerSecond: Float) {
    this.timeLeft = timeLeft
    this.damagePerSecond = damagePerSecond
  }

  abstract override def update(deltaTime: Float) {
    super.update(deltaTime)

    if (isActive) {
      timeLeft -= deltaTime
      hit(deltaTime * damagePerSecond)
    }
  }
}
