package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class FireAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.antFire

  override def velocity: Float = 3.0f
}
