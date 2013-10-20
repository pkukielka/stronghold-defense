package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}

trait Attack {
  def update(deltaTime: Float, enemies: Array[Enemy], pathFinder: PathFinder): Unit

  def isCompleted: Boolean
}
