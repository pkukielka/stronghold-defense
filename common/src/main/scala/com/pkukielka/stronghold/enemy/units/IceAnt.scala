package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{Enemy, PathFinder, EnemyAssets}

object IceAnt extends EnemyAssets("ant_ice", 0.07f)

class IceAnt(implicit pathFinder: PathFinder) extends Enemy(IceAnt, pathFinder) {
  override def velocity: Float = 2.0f
}