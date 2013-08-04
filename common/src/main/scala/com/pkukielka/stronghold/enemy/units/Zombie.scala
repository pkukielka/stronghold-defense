package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class Zombie(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.zombie

  override def velocity: Float = 0.9f
}