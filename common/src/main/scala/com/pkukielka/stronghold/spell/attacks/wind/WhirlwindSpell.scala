package com.pkukielka.stronghold.spell.attacks.wind

import com.pkukielka.stronghold.enemy.PathFinder
import com.pkukielka.stronghold.spell.{Attack, Spell}
import com.pkukielka.stronghold.assets.Assets

class WhirlwindSpell(pathFinder: PathFinder, whirlwindAttack: => Attack) extends Spell(pathFinder) {
  val assets = Assets.spellWind2
  val range = Whirlwind.range
  val lifeTime = 5f
  val shootInterval = 1f

  def attack: Attack = whirlwindAttack
}
