package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object Stealth {
  val dieAnimations = Enemy.createDieAnimations("stealth", 0.09f)
  val moveAnimations = Enemy.createMoveAnimations("stealth", 0.09f)
  val sound = new EnemySound("stealth")
}

class Stealth(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.2f

  def getDeathAnimations: Array[Animation] = Stealth.dieAnimations

  def getMoveAnimations: Array[Animation] = Stealth.moveAnimations

  def getSound: EnemySound = Stealth.sound
}
