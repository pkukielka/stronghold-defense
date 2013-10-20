package com.pkukielka.stronghold

import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait Renderer extends Ordered[Renderer] {
  def draw(batch: SpriteBatch, deltaTime: Float)

  def depth: Float

  override def compare(o: Renderer) =
    ((o.depth - depth) * 1000f).toInt

}
