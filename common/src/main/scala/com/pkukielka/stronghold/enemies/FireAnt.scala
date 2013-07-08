package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object FireAnt {
  val dieAnimations = Enemy.createDieAnimations("fire_ant", 0.05f)
  val moveAnimations = Enemy.createMoveAnimations("fire_ant", 0.05f)
}

class FireAnt(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 3f

  def getDeathAnimations: Array[Animation] = FireAnt.dieAnimations

  def getMoveAnimations: Array[Animation] = FireAnt.moveAnimations

  def getSound: EnemySound = EarthAnt.sound
}
