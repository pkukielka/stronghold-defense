package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object Skeleton extends EnemyAssets("skeleton", 0.10f)

class Skeleton(implicit pathFinder: PathFinder) extends Enemy(Skeleton, pathFinder) {
  override def velocity: Float = 2.0f
}