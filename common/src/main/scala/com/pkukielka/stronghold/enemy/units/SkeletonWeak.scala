package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object SkeletonWeak extends Assets("skeleton_weak", 0.10f)

class SkeletonWeak(implicit pathFinder: PathFinder) extends Enemy(SkeletonWeak, pathFinder) {
  override def velocity: Float = 1.5f
}