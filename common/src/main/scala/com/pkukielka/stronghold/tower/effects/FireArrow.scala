package com.pkukielka.stronghold.tower.effects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.effect.FireEffect
import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}

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
      for (enemy <- enemies if !enemy.isDead && enemy.isHit(previousPosition, position)) {
        enemy.setOnFire(1 + Math.random().toFloat * 4, 20)
      }
    }
  }

  override def draw(batch: SpriteBatch) {
    super.draw(batch)

    if (isFireActive) {
      if (fireEffect.isComplete) {
        fireEffect.free()
        isFireActive = false
      }
      else {
        fireEffect.setPosition(position.x + direction.x * 0.25f, position.y + direction.y * 0.25f)
        fireEffect.draw(batch)
      }
    }
  }
}
