package com.pkukielka.stronghold.enemies

import com.badlogic.gdx.graphics.g2d.Animation
import com.pkukielka.stronghold.PathFinder

object EliteGoblin {
  val dieAnimations = Enemy.createDieAnimations("goblin_elite", 0.06f)
  val moveAnimations = Enemy.createMoveAnimations("goblin_elite", 0.06f)
}

class EliteGoblin(implicit pathFinder: PathFinder) extends Enemy(pathFinder) {
  def getVelocity: Float = 2.5f

  def getDeathAnimations: Array[Animation] = EliteGoblin.dieAnimations

  def getMoveAnimations: Array[Animation] = EliteGoblin.moveAnimations

  def getSound: EnemySound = Goblin.sound
}

