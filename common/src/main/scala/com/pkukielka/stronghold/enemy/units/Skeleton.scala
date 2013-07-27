package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object Skeleton extends Assets("skeleton", 0.10f)

class Skeleton(implicit pathFinder: PathFinder) extends Enemy(Skeleton, pathFinder) {
  override def velocity: Float = 2.0f
}