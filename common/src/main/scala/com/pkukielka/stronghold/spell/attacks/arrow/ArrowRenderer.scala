package com.pkukielka.stronghold.spell.archer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.{SpriteBatch, Sprite}
import com.badlogic.gdx.graphics.Texture

object ArrowRenderer {
  val arrow_broken_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow_broken.png")))
  val arrow_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow.png")))
  val spriteScale = 1 / 256f
}

trait ArrowRenderer extends com.pkukielka.stronghold.Renderer {
  self: Arrow =>

  import ArrowRenderer._

  private def angle = Math.toDegrees(Math.atan2(direction.y, direction.x)).toFloat

  override def depth = position.y

  override def draw(batch: SpriteBatch, deltaTime: Float): Unit = {
    if (isCompleted) {
      batch.draw(arrow_broken_sprite, position.x, position.y, 0, 0,
        arrow_broken_sprite.getWidth, arrow_broken_sprite.getHeight, spriteScale, spriteScale, angle)
    }
    else {
      batch.draw(arrow_sprite, position.x, position.y, 0, 0,
        arrow_sprite.getWidth, arrow_sprite.getHeight, spriteScale, spriteScale, angle)
    }
  }
}
