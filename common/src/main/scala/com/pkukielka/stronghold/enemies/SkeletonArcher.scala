package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object SkeletonArcher {
  val dieAnimations = Enemy.createDieAnimations("skeleton_archer", 0.08f)
  val moveAnimations = Enemy.createMoveAnimations("skeleton_archer", 0.08f)
}

class SkeletonArcher(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.9f

  def getDeathAnimations: Array[Animation] = SkeletonArcher.dieAnimations

  def getMoveAnimations: Array[Animation] = SkeletonArcher.moveAnimations

  def getSound: EnemySound = Skeleton.sound
}
