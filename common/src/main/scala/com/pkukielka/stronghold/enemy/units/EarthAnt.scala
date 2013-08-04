package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class EarthAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.antEarth

  override def velocity: Float = 1.0f
}