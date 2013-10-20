package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.BaseEnemy
import scala.collection.mutable.ArrayBuffer

trait Tower {
  def update(deltaTime: Float, enemies: Array[BaseEnemy], bullets: ArrayBuffer[Attack])
}
