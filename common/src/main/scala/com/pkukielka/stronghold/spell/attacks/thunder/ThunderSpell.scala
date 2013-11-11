package com.pkukielka.stronghold.spell.attacks.thunder

import com.pkukielka.stronghold.enemy.{Enemy, PathFinder}
import com.pkukielka.stronghold.spell.{Spell, Attack}
import scala.collection.mutable.ArrayBuffer

class ThunderSpell(pathFinder: PathFinder, thunderAttack: => Attack) extends Spell(pathFinder) {
  override protected val assets = null
  override protected val range = Thunder.range
  override protected val lifeTime = 0.5f

  override protected def shootInterval = 0.1f + (scala.math.random * 0.2f).toFloat

  override protected def attack: Attack = thunderAttack

  private def randomOffset = 1f - (2 * scala.math.random.toFloat)

  override protected def targetAttack(enemies: Array[Enemy]) {
    val newAttack = attack
    newAttack.init(position.x + randomOffset, position.y + randomOffset, 0, 0, 0)
  }
}
