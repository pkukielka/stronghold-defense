package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object WyvernAir {
  val dieAnimations = Enemy.createDieAnimations("wyvern_air", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("wyvern_air", 0.10f)
  val sound = new EnemySound("wyvern")
}

class WyvernAir(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 5.0f

  def getDeathAnimations: Array[Animation] = WyvernAir.dieAnimations

  def getMoveAnimations: Array[Animation] = WyvernAir.moveAnimations

  def getSound: EnemySound = WyvernAir.sound
}
