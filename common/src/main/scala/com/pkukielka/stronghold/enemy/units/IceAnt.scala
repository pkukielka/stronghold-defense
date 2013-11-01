package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{EnemyCore, PathFinder}
import com.pkukielka.stronghold.assets.Assets

class IceAnt(implicit pathFinder: PathFinder) extends EnemyCore(pathFinder) {
  override def assets = Assets.antIce

  override def velocity: Float = 2.0f
}