package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object WyvernAir extends Assets("wyvern_air", 0.10f)

class WyvernAir(implicit pathFinder: PathFinder) extends Enemy(WyvernAir, pathFinder) {
  override def velocity: Float = 5.0f
}