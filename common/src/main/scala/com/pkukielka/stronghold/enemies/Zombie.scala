package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object Zombie {
  val dieAnimations = Enemy.createDieAnimations("zombie", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("zombie", 0.10f)
  val sound = new EnemySound("zombie")
}

class Zombie(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 0.9f

  def getDeathAnimations: Array[Animation] = Zombie.dieAnimations

  def getMoveAnimations: Array[Animation] = Zombie.moveAnimations

  def getSound: EnemySound = Zombie.sound
}
