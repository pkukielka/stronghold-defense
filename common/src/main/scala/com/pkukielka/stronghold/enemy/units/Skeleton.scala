package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class Skeleton(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.skeleton

  override def velocity: Float = 2.0f
}