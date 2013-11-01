package com.pkukielka.stronghold.enemy.features.flammable

trait Flammable {
  def setOnFire(timeLeft: Float, damagePerSecond: Float)
}
