package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object WyvernFire extends Assets("wyvern_fire", 0.10f)

class WyvernFire(implicit pathFinder: PathFinder) extends Enemy(WyvernFire, pathFinder) {
  override def velocity: Float = 5.0f
}