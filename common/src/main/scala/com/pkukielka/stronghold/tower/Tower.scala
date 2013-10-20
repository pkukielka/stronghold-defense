package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.Enemy
import scala.collection.mutable.ArrayBuffer

trait Tower {
  def update(deltaTime: Float, enemies: Array[Enemy], bullets: ArrayBuffer[Attack])
}
