package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object Goblin {
  val dieAnimations = Enemy.createDieAnimations("goblin", 0.07f)
  val moveAnimations = Enemy.createMoveAnimations("goblin", 0.07f)
  val sound = new EnemySound("goblin")
}

class Goblin(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 2.0f

  def getDeathAnimations: Array[Animation] = Goblin.dieAnimations

  def getMoveAnimations: Array[Animation] = Goblin.moveAnimations

  def getSound: EnemySound = Goblin.sound
}
