package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object Stealth extends EnemyAssets("stealth", 0.09f)

class Stealth(implicit pathFinder: PathFinder) extends Enemy(Stealth, pathFinder) {
  override def velocity: Float = 1.2f
}