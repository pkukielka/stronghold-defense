package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}

trait Attack {
  def update(deltaTime: Float, enemies: Array[BaseEnemy], pathFinder: PathFinder): Unit

  def isCompleted: Boolean
}
