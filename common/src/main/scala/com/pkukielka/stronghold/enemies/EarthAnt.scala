package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object EarthAnt {
  val dieAnimations = Enemy.createDieAnimations("antlion", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("antlion", 0.10f)
  val sound = new EnemySound("antlion")
}

class EarthAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.0f

  def getDeathAnimations: Array[Animation] = EarthAnt.dieAnimations

  def getMoveAnimations: Array[Animation] = EarthAnt.moveAnimations

  def getSound: EnemySound = EarthAnt.sound
}
