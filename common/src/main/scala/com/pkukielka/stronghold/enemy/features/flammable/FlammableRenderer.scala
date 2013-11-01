package com.pkukielka.stronghold.enemy.features.flammable

import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, SpriteBatch}
import com.pkukielka.stronghold.{Renderer, IsometricMapUtils}
import com.pkukielka.stronghold.enemy.EnemyRenderer
import com.pkukielka.stronghold.effect.FireEffect

trait FlammableRenderer extends Renderer with Flammable {
  self: FlammableCore with EnemyRenderer =>

  var fireEffect: ParticleEffectPool#PooledEffect = null

  abstract override def setOnFire(time: Float, damagePerSecond: Float) {
    super.setOnFire(time, damagePerSecond)

    if (fireEffect == null) {
      fireEffect = FireEffect.obtain
    }

    fireEffect.setDuration((time * 1000).toInt)
    fireEffect.reset()
  }

  abstract override def draw(batch: SpriteBatch, deltaTime: Float) {
    super.draw(batch, deltaTime)

    if (fireEffect != null) {
      if (fireEffect.isComplete) {
        fireEffect.free()
        fireEffect = null
      }
      else {
        fireEffect.setPosition(
          IsometricMapUtils.mapToCameraX(position) + width * 0.5f + getXAdjustment,
          IsometricMapUtils.mapToCameraY(position) + height * 0.5f + getYAdjustment)
        fireEffect.draw(batch, deltaTime)
      }
    }
  }
}
