package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object WyvernFire {
  val dieAnimations = Enemy.createDieAnimations("wyvern_fire", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("wyvern_fire", 0.10f)
}

class WyvernFire(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 5.0f

  def getDeathAnimations: Array[Animation] = WyvernFire.dieAnimations

  def getMoveAnimations: Array[Animation] = WyvernFire.moveAnimations

  def getSound: EnemySound = WyvernAir.sound
}
