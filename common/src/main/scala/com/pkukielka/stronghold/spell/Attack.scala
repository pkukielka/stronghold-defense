package com.pkukielka.stronghold.spell

import com.pkukielka.stronghold.enemy.Enemy

trait Attack {
  def init(xStart: Float, yStart: Float, xEnd: Float, yEnd: Float, heightsDifference: Float): Unit

  def update(deltaTime: Float, enemies: Array[Enemy]): Unit

  def isCompleted: Boolean
}
