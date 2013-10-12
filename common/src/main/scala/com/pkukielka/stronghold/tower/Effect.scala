package com.pkukielka.stronghold.tower

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}

abstract class Effect {
  def update(deltaTime: Float, enemies: Array[Enemy], pathFinder: PathFinder): Unit

  def isCompleted: Boolean

  def draw(batch: SpriteBatch): Unit
}
