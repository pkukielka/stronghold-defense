package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object SkeletonArcher extends EnemyAssets("skeleton_archer", 0.08f)

class SkeletonArcher(implicit pathFinder: PathFinder) extends Enemy(SkeletonArcher, pathFinder) {
  override def velocity: Float = 1.9f
}