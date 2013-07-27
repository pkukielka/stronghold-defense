package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object WyvernFire extends EnemyAssets("wyvern_fire", 0.10f)

class WyvernFire(implicit pathFinder: PathFinder) extends Enemy(WyvernFire, pathFinder) {
  override def velocity: Float = 5.0f
}