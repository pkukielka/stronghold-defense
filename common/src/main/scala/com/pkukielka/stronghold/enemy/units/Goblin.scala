package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class Goblin(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.goblin

  override def velocity: Float = 2.0f
}
