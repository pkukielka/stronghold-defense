package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{Enemy, PathFinder}
import com.pkukielka.stronghold.enemy.assets.Assets

object IceAnt extends Assets("ant_ice", 0.07f)

class IceAnt(implicit pathFinder: PathFinder) extends Enemy(IceAnt, pathFinder) {
  override def velocity: Float = 2.0f
}