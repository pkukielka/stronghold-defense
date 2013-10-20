package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class SkeletonMage(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.skeletonMage

  override def velocity: Float = 1.5f
}