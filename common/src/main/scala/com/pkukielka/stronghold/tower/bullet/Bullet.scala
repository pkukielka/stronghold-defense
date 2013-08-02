package com.pkukielka.stronghold.tower.bullet

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.pkukielka.stronghold.IsometricMapUtils

abstract class Bullet {
  val position: Vector2 = new Vector2()
  val target: Vector2 = new Vector2()
  var heightsDifference: Float = 0f

  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float) {
    position.set(xStart, yStart)
    target.set(xEnd, yEnd)
    this.heightsDifference = heightsDifference
  }

  def isCompleted: Boolean

  def update(deltaTime: Float)

  def draw(batch: SpriteBatch)
}
