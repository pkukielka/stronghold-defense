package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object EliteGoblin extends Assets("goblin_elite", 0.06f)

class EliteGoblin(implicit pathFinder: PathFinder) extends Enemy(EliteGoblin, pathFinder) {
  override def velocity: Float = 2.5f
}

