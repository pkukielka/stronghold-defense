package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class FireAnt(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.antFire

  override def velocity: Float = 3.0f
}
