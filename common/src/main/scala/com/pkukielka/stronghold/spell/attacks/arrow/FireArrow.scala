package com.pkukielka.stronghold.spell.attacks.arrow

import com.pkukielka.stronghold.effect.FireEffect
import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.IsometricMapUtils
import com.pkukielka.stronghold.enemy.features.flammable.FlammableCore

class FireArrow extends Arrow {
  val fireEffect = FireEffect.obtain
  var isFireActive = true

  override def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
  }

  override def update(deltaTime: Float, enemies: Array[Enemy], pathFinder: PathFinder) {
    super.update(deltaTime, enemies, pathFinder)

    if (isFireActive) {
      fireEffect.update(deltaTime)
    }

    if (!isCompleted) {
      temp.p1.set(IsometricMapUtils.cameraToMapX(previousPosition), IsometricMapUtils.cameraToMapY(previousPosition))
      temp.p2.set(IsometricMapUtils.cameraToMapX(position), IsometricMapUtils.cameraToMapY(position))
      for (enemy <- enemies if !enemy.isDead && enemy.isInstanceOf[FlammableCore] && enemy.isHit(temp.p1, temp.p2)) {
        enemy.asInstanceOf[FlammableCore].setOnFire(1 + scala.math.random.toFloat * 4, 10)
      }
    }
  }
}
