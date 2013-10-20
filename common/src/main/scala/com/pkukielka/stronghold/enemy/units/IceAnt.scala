package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{BaseEnemy, PathFinder}
import com.pkukielka.stronghold.assets.Assets

class IceAnt(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.antIce

  override def velocity: Float = 2.0f
}