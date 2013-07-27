package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object WyvernEarth extends Assets("wyvern_earth", 0.10f)

class WyvernEarth(implicit pathFinder: PathFinder) extends Enemy(WyvernEarth, pathFinder) {
  override def velocity: Float = 5.0f
}