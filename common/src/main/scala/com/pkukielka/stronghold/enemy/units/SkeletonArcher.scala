package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class SkeletonArcher(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.skeletonArcher

  override def velocity: Float = 1.9f
}