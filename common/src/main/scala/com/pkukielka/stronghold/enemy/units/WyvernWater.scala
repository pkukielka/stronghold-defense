package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, BaseEnemy}
import com.pkukielka.stronghold.assets.Assets

class WyvernWater(implicit pathFinder: PathFinder) extends BaseEnemy(pathFinder) {
  override def assets = Assets.wyvernWater

  override def velocity: Float = 5.0f
}