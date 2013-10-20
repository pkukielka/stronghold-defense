package com.pkukielka.stronghold.enemy

import com.pkukielka.stronghold.assets.EnemyAssets
import com.badlogic.gdx.math.Vector2

trait Enemy {
  def position: Vector2

  def isDead: Boolean

  def assets: EnemyAssets

  def width: Float

  def height: Float

  def velocity: Float

  def die(): Unit

  def isHit(segmentStart: Vector2, segmentEnd: Vector2): Boolean

  def hit(damage: Float): Unit

  def harm(damage: Float): Unit

  def turn(): Unit

  def setOnFire(time: Float, damagePerSecond: Float) : Unit

  def update(deltaTime: Float)
}
