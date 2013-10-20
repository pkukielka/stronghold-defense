package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class Zombie(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.zombie

  override def velocity: Float = 0.9f
}