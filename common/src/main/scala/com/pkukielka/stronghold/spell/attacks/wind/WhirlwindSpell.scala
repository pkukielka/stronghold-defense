package com.pkukielka.stronghold.spell.attacks.wind

import com.pkukielka.stronghold.enemy.PathFinder
import com.pkukielka.stronghold.spell.{Attack, Spell}
import com.pkukielka.stronghold.assets.Assets

class WhirlwindSpell(pathFinder: PathFinder, whirlwindAttack: => Attack) extends Spell(pathFinder) {
  override protected val assets = Assets.spellWind2
  override protected val range = Whirlwind.range
  override protected val lifeTime = 5f
  override protected val shootInterval = 1f

  override protected def attack: Attack = whirlwindAttack
}
