package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{Enemy, PathFinder}
import com.pkukielka.stronghold.assets.Assets

class IceAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.antIce

  override def velocity: Float = 2.0f
}