package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object Goblin extends Assets("goblin", 0.07f)

class Goblin(implicit pathFinder: PathFinder) extends Enemy(Goblin, pathFinder) {
  override def velocity: Float = 2.0f
}
