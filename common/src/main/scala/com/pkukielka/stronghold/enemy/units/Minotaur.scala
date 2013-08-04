package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, Enemy}
import com.pkukielka.stronghold.assets.Assets

class Minotaur(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  override def assets = Assets.minotaur

  override def velocity: Float = 1.4f
}
