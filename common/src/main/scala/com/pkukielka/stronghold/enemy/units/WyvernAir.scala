package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object WyvernAir extends EnemyAssets("wyvern_air", 0.10f)

class WyvernAir(implicit pathFinder: PathFinder) extends Enemy(WyvernAir, pathFinder) {
  override def velocity: Float = 5.0f
}