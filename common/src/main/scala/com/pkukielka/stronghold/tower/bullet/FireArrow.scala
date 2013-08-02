package com.pkukielka.stronghold.tower.bullet

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.effect.FireEffect

class FireArrow extends Arrow {
  val fireEffect = FireEffect.obtain
  var isFireActive = true

  override def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    super.init(xStart, yStart, xEnd, yEnd, heightsDifference)
  }

  override def update(deltaTime: Float) {
    super.update(deltaTime)
    if (isFireActive) {
      fireEffect.update(deltaTime)
    }
  }

  override def drawSpecialEffects(batch: SpriteBatch) {
    if (isFireActive)
    {
      if (fireEffect.isComplete()) {
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
