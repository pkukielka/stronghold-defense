package com.pkukielka.stronghold.enemy.units

import com.pkukielka.stronghold.enemy.{PathFinder, EnemyAssets, Enemy}

object EarthAnt extends EnemyAssets("ant_earth", 0.10f)

class EarthAnt(implicit pathFinder: PathFinder) extends Enemy(EarthAnt, pathFinder) {
  override def velocity: Float = 1.0f
}