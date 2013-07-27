package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object FireAnt extends Assets("ant_fire", 0.05f)

class FireAnt(implicit pathFinder: PathFinder) extends Enemy(FireAnt, pathFinder) {
  override def velocity: Float = 3.0f
}
