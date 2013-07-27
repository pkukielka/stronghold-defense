package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object Stealth extends Assets("stealth", 0.09f)

class Stealth(implicit pathFinder: PathFinder) extends Enemy(Stealth, pathFinder) {
  override def velocity: Float = 1.2f
}