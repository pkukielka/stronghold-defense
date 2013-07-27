package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object EarthAnt extends Assets("ant_earth", 0.10f)

class EarthAnt(implicit pathFinder: PathFinder) extends Enemy(EarthAnt, pathFinder) {
  override def velocity: Float = 1.0f
}