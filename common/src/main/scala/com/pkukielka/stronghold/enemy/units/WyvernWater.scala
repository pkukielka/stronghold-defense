package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object WyvernWater extends Assets("wyvern_water", 0.10f)

class WyvernWater(implicit pathFinder: PathFinder) extends Enemy(WyvernWater, pathFinder) {
  override def velocity: Float = 5.0f
}