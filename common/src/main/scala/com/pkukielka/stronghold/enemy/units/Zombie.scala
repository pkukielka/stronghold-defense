package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyCore}
import com.pkukielka.stronghold.assets.Assets

class Zombie(implicit pathFinder: PathFinder) extends EnemyCore(pathFinder) {
  override def assets = Assets.zombie

  override def velocity: Float = 0.9f
}