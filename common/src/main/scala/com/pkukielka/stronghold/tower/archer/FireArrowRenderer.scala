package com.pkukielka.stronghold.tower.archer

import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait FireArrowRenderer extends ArrowRenderer {
  self: FireArrow =>

  abstract override def draw(batch: SpriteBatch): Unit = {
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
