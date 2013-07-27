package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object Goblin extends EnemyAssets("goblin", 0.07f)

class Goblin(implicit pathFinder: PathFinder) extends Enemy(Goblin, pathFinder) {
  override def velocity: Float = 2.0f
}
