package com.pkukielka.stronghold.enemy

import com.pkukielka.stronghold.assets.EnemyAssets

trait Enemy {
  def assets: EnemyAssets

  def width: Float

  def height: Float

  def velocity: Float

  def die(): Unit

  def hit(damage: Float): Unit

  def harm(damage: Float): Unit

  def turn(): Unit

  def setOnFire(time: Float, damagePerSecond: Float) : Unit
}
