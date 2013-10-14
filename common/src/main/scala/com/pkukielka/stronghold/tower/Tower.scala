package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.Enemy
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch

abstract class Tower {
  def update(deltaTime: Float, enemies: Array[Enemy], bullets: ArrayBuffer[Attack])

  def draw(batch: SpriteBatch)
}
