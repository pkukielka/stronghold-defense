package com.pkukielka.stronghold.spell

import com.pkukielka.stronghold.assets.EffectGfxAssets

trait AttackProperties {
  def range: Float

  def assets: EffectGfxAssets
}
