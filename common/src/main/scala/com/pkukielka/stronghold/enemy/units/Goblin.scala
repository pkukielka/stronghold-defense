package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class Goblin(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.goblin

  override def velocity: Float = 2.0f
}
