package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object Zombie extends Assets("zombie", 0.10f)

class Zombie(implicit pathFinder: PathFinder) extends Enemy(Zombie, pathFinder) {
  override def velocity: Float = 0.9f
}