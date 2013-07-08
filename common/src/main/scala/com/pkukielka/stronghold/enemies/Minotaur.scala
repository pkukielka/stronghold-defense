package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object Minotaur {
  val dieAnimations = Enemy.createDieAnimations("minotaur", 0.10f)
  val moveAnimations = Enemy.createMoveAnimations("minotaur", 0.10f)
  val sound = new EnemySound("minotaur")
}

class Minotaur(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 1.4f

  def getDeathAnimations: Array[Animation] = Minotaur.dieAnimations

  def getMoveAnimations: Array[Animation] = Minotaur.moveAnimations

  def getSound: EnemySound = Minotaur.sound
}
