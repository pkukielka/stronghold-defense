package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class WyvernEarth(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.wyvernEarth

  override def velocity: Float = 5.0f
}