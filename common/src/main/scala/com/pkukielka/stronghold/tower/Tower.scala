package com.pkukielka.stronghold.tower

import com.pkukielka.stronghold.enemy.EnemyCore
import scala.collection.mutable.ArrayBuffer
import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait Tower {
  def update(deltaTime: Float, enemies: Array[EnemyCore], bullets: ArrayBuffer[Attack])
}
