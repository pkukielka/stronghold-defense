package com.pkukielka.stronghold.effect.spell

import com.badlogic.gdx.math.{Bezier, Vector2}

class Spell {
  var position: Vector2 = _
  val path = new Bezier[Vector2]
  var time = 0f
}
