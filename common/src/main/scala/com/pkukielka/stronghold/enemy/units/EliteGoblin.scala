package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object EliteGoblin extends EnemyAssets("goblin_elite", 0.06f)

class EliteGoblin(implicit pathFinder: PathFinder) extends Enemy(EliteGoblin, pathFinder) {
  override def velocity: Float = 2.5f
}

