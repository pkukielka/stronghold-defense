package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object WyvernEarth extends EnemyAssets("wyvern_earth", 0.10f)

class WyvernEarth(implicit pathFinder: PathFinder) extends Enemy(WyvernEarth, pathFinder) {
  override def velocity: Float = 5.0f
}