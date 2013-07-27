package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object SkeletonMage extends EnemyAssets("skeleton_mage", 0.10f)

class SkeletonMage(implicit pathFinder: PathFinder) extends Enemy(SkeletonMage, pathFinder) {
  override def velocity: Float = 1.5f
}