package com.pkukielka.stronghold.tower.archer

import com.pkukielka.stronghold.effect.FireEffect
import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.IsometricMapUtils

class FireArrow extends Arrow {
  val fireEffect = FireEffect.obtain
  var isFireActive = true

  override def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
  }

  override def update(deltaTime: Float, enemies: Array[BaseEnemy], pathFinder: PathFinder) {
    super.update(deltaTime, enemies, pathFinder)

    if (isFireActive) {
      fireEffect.update(deltaTime)
    }

    if (!isCompleted) {
      temp.p1.set(IsometricMapUtils.cameraToMapX(previousPosition), IsometricMapUtils.cameraToMapY(previousPosition))
      temp.p2.set(IsometricMapUtils.cameraToMapX(position), IsometricMapUtils.cameraToMapY(position))
      for (enemy <- enemies if !enemy.isDead && enemy.isHit(temp.p1, temp.p2)) {
        enemy.setOnFire(1 + scala.math.random.toFloat * 4, 20)
      }
    }
  }
}
