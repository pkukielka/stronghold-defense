package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object SkeletonMage {
  val dieAnimations = Enemy.createDieAnimations("skeleton_mage", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("skeleton_mage", 0.10f)
}

class SkeletonMage(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.5f

  def getDeathAnimations: Array[Animation] = SkeletonMage.dieAnimations

  def getMoveAnimations: Array[Animation] = SkeletonMage.moveAnimations

  def getSound: EnemySound = Skeleton.sound
}
