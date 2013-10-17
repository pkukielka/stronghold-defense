package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyCore}
import com.pkukielka.stronghold.assets.Assets

class Stealth(implicit pathFinder: PathFinder) extends EnemyCore(pathFinder) {
  override def assets = Assets.stealth

  override def velocity: Float = 1.2f
}