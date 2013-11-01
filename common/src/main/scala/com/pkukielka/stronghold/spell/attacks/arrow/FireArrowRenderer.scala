package com.pkukielka.stronghold.spell.archer

import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait FireArrowRenderer extends ArrowRenderer {
  self: FireArrow =>

  abstract override def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    super.draw(batch, deltaTime)

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
