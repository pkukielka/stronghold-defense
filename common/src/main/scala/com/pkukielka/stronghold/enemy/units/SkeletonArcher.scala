package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object SkeletonArcher extends Assets("skeleton_archer", 0.08f)

class SkeletonArcher(implicit pathFinder: PathFinder) extends Enemy(SkeletonArcher, pathFinder) {
  override def velocity: Float = 1.9f
}