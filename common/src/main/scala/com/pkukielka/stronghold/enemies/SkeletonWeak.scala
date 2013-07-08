package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object SkeletonWeak {
  val dieAnimations = Enemy.createDieAnimations("skeleton_weak", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("skeleton_weak", 0.10f)
}

class SkeletonWeak(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.5f

  def getDeathAnimations: Array[Animation] = SkeletonWeak.dieAnimations

  def getMoveAnimations: Array[Animation] = SkeletonWeak.moveAnimations

  def getSound: EnemySound = Skeleton.sound
}
