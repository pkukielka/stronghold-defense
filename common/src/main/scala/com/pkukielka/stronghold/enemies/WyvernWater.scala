package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object WyvernWater {
  val dieAnimations = Enemy.createDieAnimations("wyvern_water", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("wyvern_water", 0.10f)
}

class WyvernWater(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 5.0f

  def getDeathAnimations: Array[Animation] = WyvernWater.dieAnimations

  def getMoveAnimations: Array[Animation] = WyvernWater.moveAnimations

  def getSound: EnemySound = WyvernAir.sound
}
