package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object Skeleton {
  val dieAnimations = Enemy.createDieAnimations("skeleton", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("skeleton", 0.10f)
  val sound = new EnemySound("skeleton")
}

class Skeleton(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 2f

  def getDeathAnimations: Array[Animation] = Skeleton.dieAnimations

  def getMoveAnimations: Array[Animation] = Skeleton.moveAnimations

  override def getSound: EnemySound = Skeleton.sound
}
