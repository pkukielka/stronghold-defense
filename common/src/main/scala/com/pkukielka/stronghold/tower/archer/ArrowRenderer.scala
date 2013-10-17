package com.pkukielka.stronghold.tower.archer

import com.pkukielka.stronghold.tower.Renderer
import com.badlogic.gdx.graphics.g2d.{Sprite, SpriteBatch}
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx

object ArrowRenderer {
  val arrow_broken_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow_broken.png")))
  val arrow_sprite = new Sprite(new Texture(Gdx.files.internal("data/textures/bullets/arrow.png")))
  val spriteScale = 1 / 256f

}

trait ArrowRenderer extends Renderer {
  self: Arrow =>

  import ArrowRenderer._

  private def angle = Math.toDegrees(Math.atan2(direction.y, direction.x)).toFloat

  override def draw(batch: SpriteBatch): Unit = {
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
