package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object IceAnt {
  val dieAnimations = Enemy.createDieAnimations("ice_ant", 0.07f)
  val moveAnimations = Enemy.createMoveAnimations("ice_ant", 0.07f)
}

class IceAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 2.0f

  def getDeathAnimations: Array[Animation] = IceAnt.dieAnimations

  def getMoveAnimations: Array[Animation] = IceAnt.moveAnimations

  def getSound: EnemySound = EarthAnt.sound
}
