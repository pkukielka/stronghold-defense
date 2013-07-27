package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.enemy.assets.Assets

object Minotaur extends Assets("minotaur", 0.10f)

class Minotaur(implicit pathFinder: PathFinder) extends Enemy(Minotaur, pathFinder) {
  override def velocity: Float = 1.4f
}
