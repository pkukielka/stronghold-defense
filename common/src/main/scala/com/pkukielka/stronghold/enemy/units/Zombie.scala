package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object Zombie extends EnemyAssets("zombie", 0.10f)

class Zombie(implicit pathFinder: PathFinder) extends Enemy(Zombie, pathFinder) {
  override def velocity: Float = 0.9f
}