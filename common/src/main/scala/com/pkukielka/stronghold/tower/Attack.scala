package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyCore}

trait Attack {
  def update(deltaTime: Float, enemies: Array[EnemyCore], pathFinder: PathFinder): Unit

  def isCompleted: Boolean
}
