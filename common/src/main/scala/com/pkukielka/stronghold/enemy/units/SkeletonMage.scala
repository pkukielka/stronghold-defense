package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyCore}
import com.pkukielka.stronghold.assets.Assets

class SkeletonMage(implicit pathFinder: PathFinder) extends EnemyCore(pathFinder) {
  override def assets = Assets.skeletonMage

  override def velocity: Float = 1.5f
}