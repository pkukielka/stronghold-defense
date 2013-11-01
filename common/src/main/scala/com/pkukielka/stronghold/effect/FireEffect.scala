package com.pkukielka.stronghold.effect

import com.badlogic.gdx.graphics.g2d.{ParticleEffectPool, ParticleEffect}
import com.badlogic.gdx.Gdx

object FireEffect {
  val fireEffect = new ParticleEffect()
  fireEffect.load(Gdx.files.internal("data/particles/fire.p"), Gdx.files.internal("data/particles"))
  val fireEffectsPool = new ParticleEffectPool(fireEffect, 1, 20)

  def obtain = fireEffectsPool.obtain()
}
