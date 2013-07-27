package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object Minotaur extends EnemyAssets("minotaur", 0.10f)

class Minotaur(implicit pathFinder: PathFinder) extends Enemy(Minotaur, pathFinder) {
  override def velocity: Float = 1.4f
}
