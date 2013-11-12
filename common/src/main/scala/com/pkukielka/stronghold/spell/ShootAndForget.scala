package com.pkukielka.stronghold.spell

import com.pkukielka.stronghold.enemy.Enemy

trait ShootAndForget extends Attack {
  self: Attack =>

  private var alreadyShoot = false

  override abstract def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
    alreadyShoot = false
  }

  override abstract def update(deltaTime: Float, enemies: Array[Enemy]): Unit = {
    if (advanceTime(deltaTime) && !alreadyShoot) {
      alreadyShoot = true
      super.update(deltaTime, enemies)
    }
  }
}
