package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object WyvernEarth {
  val dieAnimations = Enemy.createDieAnimations("wyvern_earth", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("wyvern_earth", 0.10f)
}

class WyvernEarth(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 5.0f

  def getDeathAnimations: Array[Animation] = WyvernEarth.dieAnimations

  def getMoveAnimations: Array[Animation] = WyvernEarth.moveAnimations

  def getSound: EnemySound = WyvernAir.sound
}
